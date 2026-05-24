package com.catalog.brand.service;

import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.repository.BrandRepository;
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
}
