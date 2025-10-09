package com.ecommerce_project.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank
        @Size(min = 3, message = "Category name must be at least 3 characters long")
        String categoryName) {
    public CategoryRequestDTO {
        categoryName = categoryName.toUpperCase();
    }
}
