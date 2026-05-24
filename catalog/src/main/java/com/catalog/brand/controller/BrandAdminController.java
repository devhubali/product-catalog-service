package com.catalog.brand.controller;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.service.BrandService;
import com.catalog.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(ApiResponse.success(brandService.getAllBrands()));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandBySlug(@PathVariable String slug){
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandBySlug(slug)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@Valid @RequestBody BrandRequest brandRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Brand created successfully", brandService.createBrand(brandRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(@PathVariable UUID id, @Valid @RequestBody BrandRequest brandRequest){
        return ResponseEntity.ok(ApiResponse.success("Brand updated successfully", brandService.updateBrand(id, brandRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable UUID id){
        brandService.deleteBrand(id);
        return ResponseEntity.ok(ApiResponse.success("Brand deleted successfully", null));
    }


}
