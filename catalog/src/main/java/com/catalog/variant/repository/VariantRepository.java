package com.catalog.variant.repository;

import com.catalog.variant.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VariantRepository extends JpaRepository<Variant, UUID> {
    List<Variant> findByProductId(UUID productId);
    boolean existsBySku(String sku);
    boolean existsBySkuAndIdNot(String sku, UUID id);
}
