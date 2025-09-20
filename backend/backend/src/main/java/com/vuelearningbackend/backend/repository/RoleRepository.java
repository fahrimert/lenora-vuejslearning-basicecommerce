package com.vuelearningbackend.backend.repository;

import com.vuelearningbackend.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<com.vuelearningbackend.backend.model.Role,Long> {

   Optional<Role> findByName(String role);
}
