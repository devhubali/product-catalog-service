package com.catalog.category.service;

import com.catalog.category.dto.CategoryRequest;
import com.catalog.category.dto.CategoryResponse;
import com.catalog.category.entity.Category;
import com.catalog.category.enums.CategoryStatus;
import com.catalog.category.repository.CategoryRepository;
import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.common.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllActiveCategories() {
        return categoryRepository.findAllByStatus(CategoryStatus.ACTIVE).stream().map(CategoryResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> ResourceNotFoundException.of("Category", slug));
        return CategoryResponse.from(category);
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        String slug = SlugUtil.toSlug(request.name());
        if (categoryRepository.existsBySlug(slug)) {
            throw DuplicateResourceException.of("Category", "name", request.name());
        }
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .slug(slug)
                .status(CategoryStatus.ACTIVE)
                .build();
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Category", id));

        if (!category.getName().equals(request.name())) {
            String newSlug = SlugUtil.toSlug(request.name());
            if (categoryRepository.existsBySlug(newSlug)) {
                throw DuplicateResourceException.of("Category", "name", request.name());
            }
            category.setSlug(newSlug);
            category.setName(request.name());
        }

        category.setDescription(request.description());
        category.setImageUrl(request.imageUrl());

        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Category", id));
        categoryRepository.delete(category);
    }
}
