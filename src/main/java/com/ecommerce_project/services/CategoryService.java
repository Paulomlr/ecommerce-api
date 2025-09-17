package com.ecommerce_project.services;

import com.ecommerce_project.dtos.CategoryRequestDTO;
import com.ecommerce_project.dtos.CategoryResponseDTO;
import com.ecommerce_project.dtos.PaginatedCategoryResponseDTO;

public interface CategoryService {
    PaginatedCategoryResponseDTO getAllCategories();
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId);
    void deleteCategory(Long categoryId);
}
