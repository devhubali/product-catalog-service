package com.catalog.category.repository;

import com.catalog.category.entity.Category;
import com.catalog.category.enums.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByStatus(CategoryStatus status);

    Optional<Category> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
