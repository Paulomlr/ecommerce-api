package com.ecommerce_project.dtos.product;

import java.util.List;

public record PaginatedProductResponseDTO(List<ProductResponseDTO> products,
                                          Integer pageNumber,
                                          Integer pageSize,
                                          Long totalElements,
                                          Integer totalPages,
                                          Boolean lastPage) {
}
