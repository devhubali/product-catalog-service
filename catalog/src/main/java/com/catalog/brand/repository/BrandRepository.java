package com.catalog.brand.repository;

import com.catalog.brand.entity.Brand;
import com.catalog.brand.enums.BrandStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    List<Brand> findAllByStatus(BrandStatus status);

    Optional<Brand> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
