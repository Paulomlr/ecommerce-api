package com.ecommerce_project.dtos;

import java.util.List;

public record PaginatedCategoryResponseDTO(List<CategoryResponseDTO> categories,
                                           Integer pageNumber,
                                           Integer pageSize,
                                           Long totalElements,
                                           Integer totalPages,
                                           Boolean lastPage) {
}
