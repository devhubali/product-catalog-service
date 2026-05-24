package com.catalog.attribute.entity;

import com.catalog.common.entity.BaseEntity;
import com.catalog.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_attributes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductAttribute extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;
}
