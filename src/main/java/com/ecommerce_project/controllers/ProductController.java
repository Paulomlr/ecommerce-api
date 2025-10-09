package com.ecommerce_project.controllers;

import com.ecommerce_project.dtos.product.PaginatedProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductRequestDTO;
import com.ecommerce_project.dtos.product.ProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductUpdateRequestDTO;
import com.ecommerce_project.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/categories/{categoryId}/product")
    public ResponseEntity<ProductResponseDTO> addProduct(@PathVariable Long categoryId,
                                                         @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.createProduct(productRequestDTO, categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<PaginatedProductResponseDTO> getAllProducts(Pageable pageable) {
        var allProducts = productService.getAllProducts(pageable);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<PaginatedProductResponseDTO> getProductsByCategory(@PathVariable Long categoryId,
                                                                             Pageable pageable) {
        var products = productService.getProductsByCategory(pageable, categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/keyword/{keyword}")
    public ResponseEntity<PaginatedProductResponseDTO> getProductsByKeyword(@PathVariable String keyword,
                                                                             Pageable pageable) {
        var products = productService.getProductsByKeyword(pageable, keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId,
                                                            @RequestBody @Valid ProductUpdateRequestDTO productUpdateRequestDTO) {
        return new ResponseEntity<>(productService.updateProduct(productUpdateRequestDTO, productId), HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductResponseDTO> updateProductImage(@PathVariable Long productId,
                                                                 @RequestParam("Image") MultipartFile image) {
        return new ResponseEntity<>(productService.updateProductImage(productId, image), HttpStatus.OK);
    }
}
