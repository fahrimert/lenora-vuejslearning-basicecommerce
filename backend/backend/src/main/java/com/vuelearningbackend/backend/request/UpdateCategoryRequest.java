package com.vuelearningbackend.backend.request;

import com.vuelearningbackend.backend.model.Product;
import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private String id;
    private  String name;
    private Product products;
}
