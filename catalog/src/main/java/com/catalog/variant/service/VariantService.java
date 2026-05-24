package com.catalog.variant.service;

import com.catalog.variant.dto.VariantRequest;
import com.catalog.variant.dto.VariantResponse;

import java.util.List;
import java.util.UUID;

public interface VariantService {
    List<VariantResponse> getVariantsByProductId(UUID productId);
    VariantResponse getVariantById(UUID id);
    VariantResponse createVariant(VariantRequest request);
    VariantResponse updateVariant(UUID id, VariantRequest request);
    void deleteVariant(UUID id);
}
