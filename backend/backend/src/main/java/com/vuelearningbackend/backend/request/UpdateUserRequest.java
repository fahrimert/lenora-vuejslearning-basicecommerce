package com.vuelearningbackend.backend.request;


import lombok.Data;

@Data
//updatede de request yapıyor
public class UpdateUserRequest {
    private  String firstname;
    private  String lastname;
    private  String email;
    private  String password;
}
