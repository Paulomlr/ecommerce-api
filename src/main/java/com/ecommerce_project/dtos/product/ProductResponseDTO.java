package com.ecommerce_project.dtos.product;

public record ProductResponseDTO(Long productId,
                                 String productName,
                                 String imageUrl,
                                 String description,
                                 Integer quantity,
                                 Double price,
                                 Double discount,
                                 Double specialPrice) {
}
