package com.vuelearningbackend.backend.service;

import com.vuelearningbackend.backend.dto.UserDto;
import com.vuelearningbackend.backend.model.EcommerceUser;
import com.vuelearningbackend.backend.repository.UserRepository;
import com.vuelearningbackend.backend.request.CreateUserRequest;
import com.vuelearningbackend.backend.request.UpdateUserRequest;
import com.vuelearningbackend.backend.service.UserInterfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private  final UserRepository userRepository;
    private  final  ModelMapper modelMapper;
    private  final PasswordEncoder passwordEncoder ;


    @Override
    public EcommerceUser getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found!"));
    }

    @Override
    public EcommerceUser createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map( req -> {
                    EcommerceUser ecommerceUser =  new EcommerceUser();
                    ecommerceUser.setEmail(request.getEmail());
                    ecommerceUser.setPassword(passwordEncoder.encode((CharSequence) request.getPassword()));
                    ecommerceUser.setFirstname(request.getFirstname());
                    ecommerceUser.setLastname(request.getLastname());
                    return userRepository.save(ecommerceUser);
                }).orElseThrow(() -> new RuntimeException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public EcommerceUser updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstname(request.getFirstname());
            existingUser.setLastname(request.getLastname());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete , () -> {
            throw new RuntimeException("User doesnt exists!");
        });
    }

    @Override
    public UserDto convertToDto(EcommerceUser user) {
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public EcommerceUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
