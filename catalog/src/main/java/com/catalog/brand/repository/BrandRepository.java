package com.catalog.brand.repository;

import com.catalog.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    public List<Brand> findByStatus();

}
