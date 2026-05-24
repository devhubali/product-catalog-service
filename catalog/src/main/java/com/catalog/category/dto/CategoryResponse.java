package com.catalog.category.dto;

import com.catalog.category.entity.Category;
import com.catalog.category.enums.CategoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String slug,
        String description,
        String imageUrl,
        CategoryStatus status,
        LocalDateTime createdAt
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getImageUrl(),
                category.getStatus(),
                category.getCreatedAt()
        );
    }
}
