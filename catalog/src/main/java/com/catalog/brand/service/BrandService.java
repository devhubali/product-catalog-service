package com.catalog.brand.service;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandBySlug(String slug);
    BrandResponse createBrand(BrandRequest brandRequest);
    BrandResponse updateBrand(UUID id, BrandRequest brandRequest);
    void deleteBrand(UUID id);
}
