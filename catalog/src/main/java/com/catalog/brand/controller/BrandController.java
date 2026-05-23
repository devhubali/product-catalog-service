package com.catalog.brand.controller;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/brands/")
public class BrandController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands(){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandBySlug(@PathVariable String slug){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(@PathVariable String id){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> deleteBrand(@PathVariable String id){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@RequestParam BrandRequest brandRequest){
        return ResponseEntity.ok(ApiResponse.failure("Not implemented yet"));
    }


}
