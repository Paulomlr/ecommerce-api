package com.ecommerce_project.repositories;

import com.ecommerce_project.models.Category;
import com.ecommerce_project.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageable);
    Boolean existsByProductName(String productName);
}
