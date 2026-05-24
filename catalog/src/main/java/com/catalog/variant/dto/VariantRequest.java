package com.catalog.variant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record VariantRequest(
        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotBlank(message = "SKU is required")
        String sku,

        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock quantity must be zero or greater")
        Integer stockQuantity,

        Map<String, String> attributes
) {}
