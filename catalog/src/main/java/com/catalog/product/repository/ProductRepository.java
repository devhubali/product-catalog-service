package com.catalog.product.repository;

import com.catalog.product.entity.Product;
import com.catalog.product.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByStatus(ProductStatus status);

    Optional<Product> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsBySku(String sku);

    List<Product> findByBrandId(UUID brandId);

    List<Product> findByCategoryId(UUID categoryId);

    List<Product> findAllWithBrandAndCategory();

    List<Product> findByStatusWithBrandAndCategory(ProductStatus status);

    Optional<Product> findBySlugWithBrandAndCategory(String slug);
}
