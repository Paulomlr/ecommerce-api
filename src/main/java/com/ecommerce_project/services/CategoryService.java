package com.ecommerce_project.services;

import com.ecommerce_project.dtos.category.CategoryRequestDTO;
import com.ecommerce_project.dtos.category.CategoryResponseDTO;
import com.ecommerce_project.dtos.category.PaginatedCategoryResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    PaginatedCategoryResponseDTO getAllCategories(Pageable pageable);
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId);
    void deleteCategory(Long categoryId);
}
