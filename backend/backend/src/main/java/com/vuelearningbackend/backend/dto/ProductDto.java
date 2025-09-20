package com.vuelearningbackend.backend.dto;

import com.vuelearningbackend.backend.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private  String name;
    private  String brand;
    private  String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private List<ImageDto> images;
}
