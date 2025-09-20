package com.vuelearningbackend.backend.controller;


import com.vuelearningbackend.backend.dto.UserDto;
import com.vuelearningbackend.backend.model.EcommerceUser;
import com.vuelearningbackend.backend.request.CreateUserRequest;
import com.vuelearningbackend.backend.request.UpdateUserRequest;
import com.vuelearningbackend.backend.response.ApiResponse;
import com.vuelearningbackend.backend.service.UserInterfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private  final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") Long userId) {
        try {
            EcommerceUser user = userService.getUserById(userId);
            UserDto userDto = userService.convertToDto(user);

            return ResponseEntity.ok(new ApiResponse("success", userDto));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            EcommerceUser user = userService.createUser(request);
            UserDto userDto =userService.convertToDto(user);

            return  ResponseEntity.ok(new ApiResponse("Create User success!",userDto));
        } catch (RuntimeException e) {
            return  ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public  ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable  Long userId){
        try {
            EcommerceUser user = userService.updateUser(request,userId);
            UserDto userDto =userService.convertToDto(user);

            return  ResponseEntity.ok(new ApiResponse("update user success",userDto));
        } catch (RuntimeException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));        }
    }

    @DeleteMapping("/{userId}/delete")
    public   ResponseEntity<ApiResponse> deleteUser(@RequestBody  CreateUserRequest request, @PathVariable  Long userId) {

        try {
            userService.deleteUser(userId);
            return  ResponseEntity.ok(new ApiResponse("Delete User success!",null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
