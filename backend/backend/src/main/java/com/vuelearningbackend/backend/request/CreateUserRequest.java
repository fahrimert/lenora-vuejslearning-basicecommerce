package com.vuelearningbackend.backend.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateUserRequest {
    private  String firstname;
    private  String lastname;
    private  String email;
    private  String password;

}
