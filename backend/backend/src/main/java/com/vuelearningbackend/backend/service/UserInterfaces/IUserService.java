package com.vuelearningbackend.backend.service.UserInterfaces;

import com.vuelearningbackend.backend.dto.UserDto;
import com.vuelearningbackend.backend.model.EcommerceUser;

public interface IUserService {

    EcommerceUser getUserById(Long userId);
    EcommerceUser createUser(com.vuelearningbackend.backend.request.CreateUserRequest request);
    EcommerceUser updateUser(com.vuelearningbackend.backend.request.UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(EcommerceUser user);

    EcommerceUser getAuthenticatedUser();
}
