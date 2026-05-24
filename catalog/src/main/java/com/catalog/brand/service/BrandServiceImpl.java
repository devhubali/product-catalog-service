package com.catalog.brand.service;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.entity.Brand;
import com.catalog.brand.repository.BrandRepository;
import com.catalog.common.util.SlugUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream().map(BrandResponse::from).toList();
    }

    @Override
    public List<BrandResponse> getAllActiveBrands() {
        return brandRepository.findByStatus().stream().map(BrandResponse::from).toList();
    }

    @Override
    public BrandResponse getBrandBySlug(String slug) {
        return BrandResponse.from(brandRepository.findBySlug());
    }

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(brandRequest.name())
                .description(brandRequest.description())
                .logoUrl(brandRequest.logoUrl())
                .slug(SlugUtil.toSlug(brandRequest.name()))
                .build();
        return BrandResponse.from(brandRepository.save(brand));
    }

    
}
