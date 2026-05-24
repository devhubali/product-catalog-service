package com.catalog.attribute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductAttributeRequest(
        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Value is required")
        String value
) {}
