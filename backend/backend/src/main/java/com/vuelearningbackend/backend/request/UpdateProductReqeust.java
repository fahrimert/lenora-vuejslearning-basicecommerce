package com.vuelearningbackend.backend.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductReqeust {
    private Integer id;
    private  String name;
    private  String brand;
    private BigDecimal price;
    private  int inventory;
    private String description;
    private com.vuelearningbackend.backend.model.Category category;
}
