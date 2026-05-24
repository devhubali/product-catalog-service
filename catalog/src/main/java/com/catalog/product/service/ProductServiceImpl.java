package com.catalog.product.service;

import com.catalog.brand.entity.Brand;
import com.catalog.brand.repository.BrandRepository;
import com.catalog.category.entity.Category;
import com.catalog.category.repository.CategoryRepository;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.common.util.SlugUtil;
import com.catalog.product.dto.ProductRequest;
import com.catalog.product.entity.Product;
import com.catalog.product.enums.ProductStatus;
import com.catalog.product.dto.ProductResponse;
import com.catalog.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllWithBrandAndCategory()
                .stream().map(ProductResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllActiveProducts() {
        return productRepository.findByStatusWithBrandAndCategory(ProductStatus.ACTIVE)
                .stream().map(ProductResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlugWithBrandAndCategory(slug)
                .orElseThrow(() -> ResourceNotFoundException.of("Product", slug));
        return ProductResponse.from(product);
    }


    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Brand brand = null;
        if (request.brandId() != null) {
            brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Brand", request.brandId()));
        }

        Category category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Category", request.categoryId()));
        }

        return ProductResponse.from(productRepository.save(
                Product.builder()
                        .name(request.name())
                        .sku(request.sku())
                        .price(request.price())
                        .status(ProductStatus.ACTIVE)
                        .imageUrl(request.imageUrl())
                        .description(request.description())
                        .slug(SlugUtil.toSlug(request.name()))
                        .brand(brand)
                        .category(category)
                        .build()
            )
        );
    }


    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Product", id));

        if (!product.getName().equals(request.name())) {
            product.setSlug(SlugUtil.toSlug(request.name()));
            product.setName(request.name());
        }

        product.setDescription(request.description());
        product.setSku(request.sku());
        product.setPrice(request.price());
        product.setImageUrl(request.imageUrl());

        if (request.brandId() != null) {
            Brand brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Brand", request.brandId()));
            product.setBrand(brand);
        } else {
            product.setBrand(null);
        }

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Category", request.categoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        return ProductResponse.from(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Product", id));
        productRepository.delete(product);
    }


}
