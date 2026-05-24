package com.catalog.brand.entity;

import com.catalog.brand.enums.BrandStatus;
import com.catalog.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Brand extends BaseEntity {
    /* Columns:
     * name: String
     * slug: String
     * description: String
     * logoUrl: String
     * status: BrandStatus
     */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String logoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BrandStatus status;
}
