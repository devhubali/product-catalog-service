package com.catalog.brand.service;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.entity.Brand;
import com.catalog.brand.enums.BrandStatus;
import com.catalog.brand.repository.BrandRepository;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.common.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        return brandRepository.findByStatus(BrandStatus.ACTIVE).stream().map(BrandResponse::from).toList();
    }

    @Override
    public BrandResponse getBrandBySlug(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> ResourceNotFoundException.of("Brand", slug));
        return BrandResponse.from(brand);
    }

    @Override
    @Transactional
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(brandRequest.name())
                .description(brandRequest.description())
                .logoUrl(brandRequest.logoUrl())
                .slug(SlugUtil.toSlug(brandRequest.name()))
                .status(BrandStatus.ACTIVE)
                .build();
        return BrandResponse.from(brandRepository.save(brand));
    }

    @Override
    @Transactional
    public BrandResponse updateBrand(UUID id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Brand", id));

        if (!brand.getName().equals(brandRequest.name())) {
            brand.setSlug(SlugUtil.toSlug(brandRequest.name()));
            brand.setName(brandRequest.name());
        }

        brand.setDescription(brandRequest.description());
        brand.setLogoUrl(brandRequest.logoUrl());

        return BrandResponse.from(brandRepository.save(brand));
    }

    @Override
    @Transactional
    public void deleteBrand(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Brand", id));
        brandRepository.delete(brand);
    }
}
