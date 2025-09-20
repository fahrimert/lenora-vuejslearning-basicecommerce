package com.vuelearningbackend.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@Getter
@Setter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String fileType;
    private String filePath;

    @Lob
    private Blob image;
    private  String downloadUrl;
    @ManyToOne
    @JoinColumn(name = "product_ıd")
    private Product product;



}
