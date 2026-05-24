package com.catalog.product.service;

import com.catalog.brand.repository.BrandRepository;
import com.catalog.category.repository.CategoryRepository;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.product.entity.Product;
import com.catalog.product.enums.ProductStatus;
import com.catalog.product.dto.ProductResponse;
import com.catalog.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductServiceImpl extends ProductService {
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


}
