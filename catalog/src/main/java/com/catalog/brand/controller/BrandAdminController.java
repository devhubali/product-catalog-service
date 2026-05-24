package com.catalog.brand.controller;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.service.BrandService;
import com.catalog.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/brands")
public class BrandAdminController {
    private final BrandService brandService;

    public BrandAdminController(BrandService brandService){
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands(){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandBySlug(@PathVariable String slug){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(@PathVariable UUID id, @RequestBody BrandRequest brandRequest){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable UUID id){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@RequestBody BrandRequest brandRequest){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }


}
