package com.ecommerce_project.dtos.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequestDTO(@Size(min = 3, message = "Product name must be at least 3 characters long")
                                      String productName,
                                      String description,
                                      @Positive Integer quantity,
                                      @Positive Double price,
                                      @Positive Double discount,
                                      Long categoryId) {
}
