package com.vuelearningbackend.backend.service.UserInterfaces;

import com.vuelearningbackend.backend.dto.ProductDto;
import com.vuelearningbackend.backend.model.Product;
import com.vuelearningbackend.backend.request.AddProductRequest;
import com.vuelearningbackend.backend.request.UpdateProductReqeust;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest addProductRequest);

    void  deleteProductById(Long id);

    List<Product> findByBrandAndName(String brand, String name);

    Product updateProductById(Long id, UpdateProductReqeust product);

    Product updateExistingProduct(Product existingProduct, UpdateProductReqeust reqeust);

    Product getProductsById(Long id);


    List<Product> findByCategory_Name(String name);
    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrand(String brand);
    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);

    List<ProductDto> getAAllProducts();

}
