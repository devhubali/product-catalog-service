package com.catalog.variant.service;

import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.product.entity.Product;
import com.catalog.product.repository.ProductRepository;
import com.catalog.variant.dto.VariantRequest;
import com.catalog.variant.dto.VariantResponse;
import com.catalog.variant.entity.Variant;
import com.catalog.variant.repository.VariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    public VariantServiceImpl(VariantRepository variantRepository, ProductRepository productRepository) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VariantResponse> getVariantsByProductId(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw ResourceNotFoundException.of("Product", productId);
        }
        return variantRepository.findByProductId(productId)
                .stream().map(VariantResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VariantResponse getVariantById(UUID id) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Variant", id));
        return VariantResponse.from(variant);
    }

    @Override
    @Transactional
    public VariantResponse createVariant(VariantRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> ResourceNotFoundException.of("Product", request.productId()));

        if (variantRepository.existsBySku(request.sku())) {
            throw DuplicateResourceException.of("Variant", "sku", request.sku());
        }

        Variant variant = Variant.builder()
                .product(product)
                .sku(request.sku())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .status(Variant.VariantStatus.ACTIVE)
                .attributes(request.attributes() != null ? request.attributes() : new java.util.HashMap<>())
                .build();

        return VariantResponse.from(variantRepository.save(variant));
    }

    @Override
    @Transactional
    public VariantResponse updateVariant(UUID id, VariantRequest request) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Variant", id));

        if (variantRepository.existsBySkuAndIdNot(request.sku(), id)) {
            throw DuplicateResourceException.of("Variant", "sku", request.sku());
        }

        if (request.productId() != null && !request.productId().equals(variant.getProduct().getId())) {
            Product product = productRepository.findById(request.productId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Product", request.productId()));
            variant.setProduct(product);
        }

        variant.setSku(request.sku());
        variant.setPrice(request.price());
        variant.setStockQuantity(request.stockQuantity());

        if (request.attributes() != null) {
            variant.getAttributes().clear();
            variant.getAttributes().putAll(request.attributes());
        }

        return VariantResponse.from(variantRepository.save(variant));
    }

    @Override
    @Transactional
    public void deleteVariant(UUID id) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Variant", id));
        variantRepository.delete(variant);
    }
}
