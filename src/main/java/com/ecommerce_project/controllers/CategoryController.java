package com.ecommerce_project.controllers;

import com.ecommerce_project.dtos.category.CategoryRequestDTO;
import com.ecommerce_project.dtos.category.CategoryResponseDTO;
import com.ecommerce_project.dtos.category.PaginatedCategoryResponseDTO;
import com.ecommerce_project.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<PaginatedCategoryResponseDTO> getAllCategories(Pageable pageable) {
        var allCategories = categoryService.getAllCategories(pageable);
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        var category = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO>updateCategory(@PathVariable Long categoryId,
                                                             @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        var category = categoryService.updateCategory(categoryRequestDTO, categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
