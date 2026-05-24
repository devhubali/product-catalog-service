package com.catalog.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        @NotBlank(message = "SKU is required")
        String sku,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        String imageUrl,
        UUID brandId,
        UUID categoryId
) {}
