package com.catalog.brand.dto;

import com.catalog.brand.entity.Brand;
import com.catalog.brand.enums.BrandStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record BrandResponse(
        UUID id,
        String name,
        String slug,
        String description,
        String logoUrl,
        BrandStatus status,
        LocalDateTime createdAt
) {
    public static BrandResponse from(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getSlug(),
                brand.getDescription(),
                brand.getLogoUrl(),
                brand.getStatus(),
                brand.getCreatedAt()
        );
    }
}
