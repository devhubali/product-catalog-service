package com.catalog.variant.controller;

import com.catalog.common.response.ApiResponse;
import com.catalog.variant.dto.VariantRequest;
import com.catalog.variant.dto.VariantResponse;
import com.catalog.variant.service.VariantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/variants")
public class VariantAdminController {

    private final VariantService variantService;

    public VariantAdminController(VariantService variantService) {
        this.variantService = variantService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<VariantResponse>>> getVariantsByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(ApiResponse.success(variantService.getVariantsByProductId(productId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VariantResponse>> getVariantById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(variantService.getVariantById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VariantResponse>> createVariant(@Valid @RequestBody VariantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Variant created successfully", variantService.createVariant(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VariantResponse>> updateVariant(@PathVariable UUID id, @Valid @RequestBody VariantRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Variant updated successfully", variantService.updateVariant(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVariant(@PathVariable UUID id) {
        variantService.deleteVariant(id);
        return ResponseEntity.ok(ApiResponse.success("Variant deleted successfully", null));
    }
}
