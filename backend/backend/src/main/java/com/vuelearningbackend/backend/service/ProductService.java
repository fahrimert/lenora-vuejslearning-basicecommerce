package com.vuelearningbackend.backend.service;

import com.vuelearningbackend.backend.dto.ImageDto;
import com.vuelearningbackend.backend.dto.ProductDto;
import com.vuelearningbackend.backend.exceptions.ProductNotFoundException;
import com.vuelearningbackend.backend.model.Category;
//import com.vuelearningbackend.backend.model.Images;
import com.vuelearningbackend.backend.model.Product;
import com.vuelearningbackend.backend.repository.CategoryRepository;
import com.vuelearningbackend.backend.repository.ProductRepository;
import com.vuelearningbackend.backend.request.AddProductRequest;
import com.vuelearningbackend.backend.request.UpdateProductReqeust;
import com.vuelearningbackend.backend.response.ApiResponse;
import com.vuelearningbackend.backend.service.UserInterfaces.IProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private  final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;
//    private  final ImagesRepository imagesRepository;
    private  final  ModelMapper modelMapper = new ModelMapper();



    @Override
    public Product addProduct(AddProductRequest addProductRequest){
//        if (!findByBrandAndName(addProductRequest.getName(), addProductRequest.getBrand()).isEmpty()){
//            throw new RuntimeException(addProductRequest.getBrand() + " " + addProductRequest.getName());
//        }

        Optional<Category> category = categoryRepository.findById(addProductRequest.getCategoryId());

        System.out.println(addProductRequest.getName());
        System.out.println(addProductRequest.getDescription());
        System.out.println(addProductRequest.getBrand());
        System.out.println(addProductRequest.getPrice());
        System.out.println(addProductRequest.getCategoryId());
        System.out.println(addProductRequest.getInventory());

        System.out.println(category.get().getName());

        System.out.println(category.get().getProducts());
        System.out.println(category.get().getName());
       if (category.isPresent()) {
           Category category1 = category.get();
           return productRepository.save(createProduct(addProductRequest,category1));


       }
        Category category1 = category.get();

        return productRepository.save(createProduct(addProductRequest,category1));

    }

    private  Product createProduct(AddProductRequest addProductRequest, Category category) {

        System.out.println(addProductRequest.getName());
        System.out.println(addProductRequest.getCategoryId());
        System.out.println(addProductRequest.getInventory());
        System.out.println(addProductRequest.getPrice());
        System.out.println(addProductRequest.getBrand());
        System.out.println(addProductRequest.getDescription());
        System.out.println(category.getProducts());
        System.out.println(category.getName());
        System.out.println(category.getId());


        return  new Product(
                addProductRequest.getName(),
                addProductRequest.getBrand(),
                addProductRequest.getPrice(),
                addProductRequest.getInventory(),
                addProductRequest.getDescription(),
                category
        );
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Product updateProductById(Long id, UpdateProductReqeust request) {
        System.out.println(request);
        try{
            System.out.println(productRepository.findById(id)
                    .map(existingProduct -> updateExistingProduct(existingProduct,request))
                    .map(productRepository::save)
                    .orElseThrow(() -> new ProductNotFoundException("Product Not Found")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

    }

    @Override
    public Product updateExistingProduct(Product existingProduct, UpdateProductReqeust reqeust) {

        try{
            existingProduct.setName(reqeust.getName());
            existingProduct.setBrand(reqeust.getBrand());
            existingProduct.setPrice(reqeust.getPrice());
            existingProduct.setInventory(reqeust.getInventory());
            existingProduct.setDescription(reqeust.getDescription());
            Category category =  categoryRepository.findByName(existingProduct.getCategory().getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        existingProduct.setName(reqeust.getName());
        existingProduct.setBrand(reqeust.getBrand());
        existingProduct.setPrice(reqeust.getPrice());
        existingProduct.setInventory(reqeust.getInventory());
        existingProduct.setDescription(reqeust.getDescription());
        Category category =  categoryRepository.findByName(existingProduct.getCategory().getName());

        existingProduct.setCategory(category);
        return existingProduct;
    }



    @Override
    public Product getProductsById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public List<Product> findByCategory_Name(String name) {
        return productRepository.findByCategory_Name(name);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return  productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return  productRepository.findByBrand(brand);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        List<Images> images = product.getImages();
//        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image,ImageDto.class)).toList();
//        productDto.setImages(imageDtos);

        return productDto;
    }

    @Override
    public List<ProductDto> getAAllProducts() {
        return  productRepository.findAll().stream().map(this::convertToDto).toList();
    }
}
