package com.catalog.brand.dto;

import jakarta.validation.constraints.NotBlank;

public record BrandRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        String logoUrl
){}
