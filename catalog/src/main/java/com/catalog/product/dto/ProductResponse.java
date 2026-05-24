package com.catalog.product.dto;

import com.catalog.product.entity.Product;
import com.catalog.product.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String slug,
        String description,
        String sku,
        BigDecimal price,
        String imageUrl,
        ProductStatus status,
        UUID brandId,
        String brandName,
        UUID categoryId,
        String categoryName,
        LocalDateTime createdAt
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                product.getSku(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStatus(),
                product.getBrand() != null ? product.getBrand().getId() : null,
                product.getBrand() != null ? product.getBrand().getName() : null,
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getCreatedAt()
        );
    }
}
