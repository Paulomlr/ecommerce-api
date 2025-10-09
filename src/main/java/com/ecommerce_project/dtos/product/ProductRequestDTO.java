package com.ecommerce_project.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(@NotBlank
                                @Size(min = 3, message = "Product name must be at least 3 characters long")
                                String productName,
                                //@NotBlank
                                //String imageUrl,
                                @NotBlank @NotNull
                                String description,
                                @Positive @NotNull
                                Integer quantity,
                                @Positive @NotNull
                                Double price,
                                @Positive
                                Double discount) {
}
