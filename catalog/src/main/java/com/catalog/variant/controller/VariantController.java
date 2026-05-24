package com.catalog.variant.controller;

import com.catalog.common.response.ApiResponse;
import com.catalog.variant.dto.VariantResponse;
import com.catalog.variant.service.VariantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/{productId}/variants")
public class VariantController {

    private final VariantService variantService;

    public VariantController(VariantService variantService) {
        this.variantService = variantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VariantResponse>>> getVariantsByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(ApiResponse.success(variantService.getVariantsByProductId(productId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VariantResponse>> getVariantById(@PathVariable UUID productId, @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(variantService.getVariantById(id)));
    }
}
