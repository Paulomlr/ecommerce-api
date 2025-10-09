package com.ecommerce_project.services.impl;

import com.ecommerce_project.configs.ProductMapper;
import com.ecommerce_project.dtos.product.PaginatedProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductRequestDTO;
import com.ecommerce_project.dtos.product.ProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductUpdateRequestDTO;
import com.ecommerce_project.exceptions.ApiException;
import com.ecommerce_project.exceptions.ResourceNotFoundException;
import com.ecommerce_project.models.Category;
import com.ecommerce_project.models.Product;
import com.ecommerce_project.repositories.CategoryRepository;
import com.ecommerce_project.repositories.ProductRepository;
import com.ecommerce_project.services.FileStorageService;
import com.ecommerce_project.services.ProductService;
import com.ecommerce_project.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT = "Product";
    private static final String CATEGORY = "Category";

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final FileStorageService fileStorageService;

    @Value("${project.image}")
    private String path;

    @Override
    public PaginatedProductResponseDTO getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return mapToPaginatedResponse(products);
    }

    @Override
    public PaginatedProductResponseDTO getProductsByCategory(Pageable pageable, Long categoryId) {
        Category category = EntityUtils.findOrThrow(categoryId, categoryRepository, CATEGORY);

        Page<Product> products = productRepository.findByCategory(category, pageable);
        return mapToPaginatedResponse(products);
    }

    @Override
    public PaginatedProductResponseDTO getProductsByKeyword(Pageable pageable, String keyword) {
        Page<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageable);
        return mapToPaginatedResponse(products);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long categoryId) {
        if(productRepository.existsByProductName(productRequestDTO.productName()))
            throw new ApiException("Product already exists");

        Category category = EntityUtils.findOrThrow(categoryId, categoryRepository, CATEGORY);

        Product product = productMapper.toEntity(productRequestDTO);
        product.setImageUrl("default.png");
        product.setCategory(category);
        product.setDiscount(productRequestDTO.discount() == null ? 0.0 : productRequestDTO.discount());
        product.recalculateSpecialPrice();

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(ProductUpdateRequestDTO productRequest, Long productId) {
        Product product = EntityUtils.findOrThrow(productId, productRepository, PRODUCT);
        productMapper.updateProductFromDto(productRequest, product);
        product.recalculateSpecialPrice();

        if(productRequest.categoryId() != null) {
            Category category = EntityUtils.findOrThrow(productRequest.categoryId(), categoryRepository, CATEGORY);
            product.setCategory(category);
        }
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProductImage(Long productId, MultipartFile image) {
        Product product = EntityUtils.findOrThrow(productId, productRepository, PRODUCT);
        String fileName = fileStorageService.save(path, image);
        product.setImageUrl(fileName);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(PRODUCT, productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public PaginatedProductResponseDTO mapToPaginatedResponse(Page<Product> products) {
        List<ProductResponseDTO> productResponseDTO = products.stream()
                .map(productMapper::toDTO)
                .toList();

        return new PaginatedProductResponseDTO(
                productResponseDTO,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast()
        );
    }
}
