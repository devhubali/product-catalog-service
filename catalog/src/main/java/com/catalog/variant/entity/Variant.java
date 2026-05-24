package com.catalog.variant.entity;

import com.catalog.common.entity.BaseEntity;
import com.catalog.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "variants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Variant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VariantStatus status;

    @ElementCollection
    @CollectionTable(
            name = "variant_attributes",
            joinColumns = @JoinColumn(name = "variant_id")
    )
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value")
    @Builder.Default
    private Map<String, String> attributes = new HashMap<>();

    public enum VariantStatus {
        ACTIVE,
        INACTIVE
    }
}
