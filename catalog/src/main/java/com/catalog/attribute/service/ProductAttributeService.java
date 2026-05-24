package com.catalog.attribute.service;

import com.catalog.attribute.dto.ProductAttributeRequest;
import com.catalog.attribute.dto.ProductAttributeResponse;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeService {
    List<ProductAttributeResponse> getAttributesByProductId(UUID productId);
    ProductAttributeResponse getAttributeById(UUID id);
    ProductAttributeResponse createAttribute(ProductAttributeRequest request);
    ProductAttributeResponse updateAttribute(UUID id, ProductAttributeRequest request);
    void deleteAttribute(UUID id);
}
