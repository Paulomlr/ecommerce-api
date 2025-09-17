package com.ecommerce_project.dtos;

import java.util.List;

public record PaginatedCategoryResponseDTO(List<CategoryResponseDTO> categories) {
}
