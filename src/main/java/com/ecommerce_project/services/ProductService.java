package com.ecommerce_project.services;

import com.ecommerce_project.dtos.product.PaginatedProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductRequestDTO;
import com.ecommerce_project.dtos.product.ProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductUpdateRequestDTO;
import com.ecommerce_project.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    PaginatedProductResponseDTO getAllProducts(Pageable pageable);
    PaginatedProductResponseDTO getProductsByCategory(Pageable pageable, Long categoryId);
    PaginatedProductResponseDTO getProductsByKeyword(Pageable pageable, String keyword);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long categoryId);
    ProductResponseDTO updateProduct(ProductUpdateRequestDTO productRequest, Long productId);
    ProductResponseDTO updateProductImage(Long productId, MultipartFile image);
    void deleteProduct(Long productId);
    PaginatedProductResponseDTO mapToPaginatedResponse(Page<Product> products);
}
