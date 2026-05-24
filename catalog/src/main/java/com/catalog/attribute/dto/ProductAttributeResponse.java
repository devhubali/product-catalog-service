package com.catalog.attribute.dto;

import com.catalog.attribute.entity.ProductAttribute;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductAttributeResponse(
        UUID id,
        UUID productId,
        String name,
        String value,
        LocalDateTime createdAt
) {
    public static ProductAttributeResponse from(ProductAttribute attribute) {
        return new ProductAttributeResponse(
                attribute.getId(),
                attribute.getProduct().getId(),
                attribute.getName(),
                attribute.getValue(),
                attribute.getCreatedAt()
        );
    }
}
