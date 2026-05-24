package com.catalog.attribute.service;

import com.catalog.attribute.dto.ProductAttributeRequest;
import com.catalog.attribute.dto.ProductAttributeResponse;
import com.catalog.attribute.entity.ProductAttribute;
import com.catalog.attribute.repository.ProductAttributeRepository;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.product.entity.Product;
import com.catalog.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository attributeRepository;
    private final ProductRepository productRepository;

    public ProductAttributeServiceImpl(ProductAttributeRepository attributeRepository,
                                       ProductRepository productRepository) {
        this.attributeRepository = attributeRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeResponse> getAttributesByProductId(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw ResourceNotFoundException.of("Product", productId);
        }
        return attributeRepository.findByProductId(productId)
                .stream().map(ProductAttributeResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductAttributeResponse getAttributeById(UUID id) {
        ProductAttribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("ProductAttribute", id));
        return ProductAttributeResponse.from(attribute);
    }

    @Override
    @Transactional
    public ProductAttributeResponse createAttribute(ProductAttributeRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> ResourceNotFoundException.of("Product", request.productId()));

        ProductAttribute attribute = ProductAttribute.builder()
                .product(product)
                .name(request.name())
                .value(request.value())
                .build();

        return ProductAttributeResponse.from(attributeRepository.save(attribute));
    }

    @Override
    @Transactional
    public ProductAttributeResponse updateAttribute(UUID id, ProductAttributeRequest request) {
        ProductAttribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("ProductAttribute", id));

        if (request.productId() != null && !request.productId().equals(attribute.getProduct().getId())) {
            Product product = productRepository.findById(request.productId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Product", request.productId()));
            attribute.setProduct(product);
        }

        attribute.setName(request.name());
        attribute.setValue(request.value());

        return ProductAttributeResponse.from(attributeRepository.save(attribute));
    }

    @Override
    @Transactional
    public void deleteAttribute(UUID id) {
        ProductAttribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("ProductAttribute", id));
        attributeRepository.delete(attribute);
    }
}
