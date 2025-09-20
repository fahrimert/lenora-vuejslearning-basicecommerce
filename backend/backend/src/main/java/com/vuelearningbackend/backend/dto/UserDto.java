package com.vuelearningbackend.backend.dto;

import lombok.Data;

@Data
//dtonun genel görevi eğer user modelini direkt döndürseydik tonla özellik döndüreceği yerde dto sayesinde bi tık daha soyutlanmış data gönderiyor
public class UserDto {

    private  String firstname;
    private  String lastname;
    private  String email;

}
