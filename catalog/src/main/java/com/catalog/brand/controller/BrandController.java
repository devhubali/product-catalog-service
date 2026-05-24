package com.catalog.brand.controller;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.service.BrandService;
import com.catalog.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService){
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllActiveBrands(){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandBySlug(@PathVariable String slug){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }


}
