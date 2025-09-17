package com.ecommerce_project.services.impl;

import com.ecommerce_project.configs.CategoryMapper;
import com.ecommerce_project.dtos.CategoryRequestDTO;
import com.ecommerce_project.dtos.CategoryResponseDTO;
import com.ecommerce_project.dtos.PaginatedCategoryResponseDTO;
import com.ecommerce_project.exceptions.ApiException;
import com.ecommerce_project.exceptions.ResourceNotFoundException;
import com.ecommerce_project.models.Category;
import com.ecommerce_project.repositories.CategoryRepository;
import com.ecommerce_project.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public PaginatedCategoryResponseDTO getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryResponseDTO = categories.stream()
                .map(categoryMapper::toDTO)
                .toList();

        return new PaginatedCategoryResponseDTO(categoryResponseDTO);
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if(isCategoryNameUnique(categoryRequestDTO.categoryName())) {
            throw new ApiException("Category already exists");
        }

        Category categorySaved = categoryRepository.save(categoryMapper.toEntity(categoryRequestDTO));
        return categoryMapper.toDTO(categorySaved);
    }

    @Override
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));

        if(isCategoryNameUnique(categoryRequestDTO.categoryName())) {
            throw new ApiException("Category already exists");
        }

        category.setCategoryName(categoryRequestDTO.categoryName());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found");
        }
        categoryRepository.deleteById(categoryId);
    }

    private Boolean isCategoryNameUnique(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }
}
