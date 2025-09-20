package com.vuelearningbackend.backend.request;

import com.vuelearningbackend.backend.model.Category;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private  String name;
    private  String brand;
    private BigDecimal price;
    private  int inventory;
    private String description;
    @Nullable
    private Category category;
}
