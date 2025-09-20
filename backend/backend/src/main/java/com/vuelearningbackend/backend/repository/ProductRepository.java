package com.vuelearningbackend.backend.repository;

import com.vuelearningbackend.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategory_Name(String name);

    List<Product> findByBrandAndName(String brand, String name);


    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);


}
