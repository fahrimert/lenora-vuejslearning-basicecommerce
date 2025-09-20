package com.vuelearningbackend.backend.repository;

import com.vuelearningbackend.backend.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images,Long> {
@Override
List<Images> findAll();

List<Images> findByProductId(Long id);

}
