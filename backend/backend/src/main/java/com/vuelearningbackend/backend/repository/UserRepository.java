package com.vuelearningbackend.backend.repository;

import com.vuelearningbackend.backend.model.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<EcommerceUser,Long> {
    boolean existsByEmail(String email);
    EcommerceUser findByEmail(String email);
}
