package com.vuelearningbackend.backend.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long imageId;
    private  String fileType;
    private String downloadUrl;
    //responseleri dtolarla döndürebiliyoz herhalde
}
