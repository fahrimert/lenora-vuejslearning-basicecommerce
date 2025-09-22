package com.vuelearningbackend.backend.controller;

import com.vuelearningbackend.backend.dto.ProductDto;
import com.vuelearningbackend.backend.model.Product;
import com.vuelearningbackend.backend.request.AddProductRequest;
import com.vuelearningbackend.backend.request.UpdateProductReqeust;
import com.vuelearningbackend.backend.response.ApiResponse;
import com.vuelearningbackend.backend.service.UserInterfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(){
        try{
            List<ProductDto> productDtoList = productService.getAAllProducts();

            return ResponseEntity.ok(new ApiResponse("success",productDtoList));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }


    }



    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try{
            Product product = productService.getProductsById(productId);
            ProductDto productDto = productService.convertToDto(product);

            return ResponseEntity.ok(new ApiResponse("success",productDto));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }


    }

    @PostMapping("/create-products")
    @PreAuthorize("hasRole('ecommerceadmin')")
    public  Product createProduct(@RequestBody AddProductRequest productRequest){
        return productService.addProduct(productRequest);
    }


    @PostMapping("/addProduct")
    public  ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest){
        try{
            Product theProduct = productService.addProduct(productRequest);
            ProductDto productDto = productService.convertToDto(theProduct);
            return  ResponseEntity.ok(new ApiResponse("Add Product Succeed",productDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{productId}/update")
    public  ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductReqeust updateProductReqeust , @PathVariable Long productId){

        try{
            Product theProduct = productService.updateProductById(productId,updateProductReqeust);
            return  ResponseEntity.ok(new ApiResponse("Update Product Success",theProduct));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return  ResponseEntity.ok(new ApiResponse("Delete Product Success",productId));
        } catch (Exception e) {
            return   ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete Product Success",productId));
        }
    }


    @GetMapping("/product/category-and-brand")
    public  ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            List<ProductDto> productDto = productService.getConvertedProducts(products);

            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return  ResponseEntity.ok(new ApiResponse("succces",productDto));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/by-brand")
    public  ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> productDto = productService.getConvertedProducts(products);

            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return  ResponseEntity.ok(new ApiResponse("succces",productDto));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{category}/allProductsByCategory")
    public  ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.findByCategory_Name(category);
            List<ProductDto> productDto = productService.getConvertedProducts(products);

            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return  ResponseEntity.ok(new ApiResponse("succces",productDto));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
