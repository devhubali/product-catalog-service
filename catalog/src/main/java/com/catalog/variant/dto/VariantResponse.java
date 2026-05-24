package com.catalog.variant.dto;

import com.catalog.variant.entity.Variant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record VariantResponse(
        UUID id,
        UUID productId,
        String sku,
        BigDecimal price,
        int stockQuantity,
        Variant.VariantStatus status,
        Map<String, String> attributes,
        LocalDateTime createdAt
) {
    public static VariantResponse from(Variant variant) {
        return new VariantResponse(
                variant.getId(),
                variant.getProduct().getId(),
                variant.getSku(),
                variant.getPrice(),
                variant.getStockQuantity(),
                variant.getStatus(),
                variant.getAttributes(),
                variant.getCreatedAt()
        );
    }
}
