package com.catalog.variant.controller;

import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.variant.dto.VariantRequest;
import com.catalog.variant.dto.VariantResponse;
import com.catalog.variant.entity.Variant;
import com.catalog.variant.service.VariantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link VariantAdminController} (admin-only endpoints).
 */
@ExtendWith(MockitoExtension.class)
class VariantAdminControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VariantService variantService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new VariantAdminController(variantService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private final UUID productId = UUID.randomUUID();
    private final UUID variantId = UUID.randomUUID();

    private VariantResponse sampleVariant() {
        return new VariantResponse(
                variantId,
                productId,
                "SKU-VAR-001",
                new BigDecimal("139.99"),
                50,
                Variant.VariantStatus.ACTIVE,
                Map.of("size", "L", "color", "red"),
                LocalDateTime.now()
        );
    }

    /**
     * GET /api/v1/admin/variants/product/{productId}
     * Should return 200 with a list of variants.
     */
    @Test
    void getVariantsByProductId_shouldReturn200WithList() throws Exception {
        when(variantService.getVariantsByProductId(productId)).thenReturn(List.of(sampleVariant()));

        mockMvc.perform(get("/api/v1/admin/variants/product/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].sku").value("SKU-VAR-001"));
    }

    /**
     * POST /api/v1/admin/variants
     * Should return 201 and the created variant when the request is valid.
     */
    @Test
    void createVariant_shouldReturn201WhenValid() throws Exception {
        VariantRequest request = new VariantRequest(productId, "SKU-VAR-001", new BigDecimal("139.99"), 50, Map.of("size", "L"));
        when(variantService.createVariant(any())).thenReturn(sampleVariant());

        mockMvc.perform(post("/api/v1/admin/variants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.sku").value("SKU-VAR-001"));
    }

    /**
     * POST /api/v1/admin/variants
     * Should return 400 when SKU is blank.
     */
    @Test
    void createVariant_shouldReturn400WhenSkuIsBlank() throws Exception {
        VariantRequest request = new VariantRequest(productId, "", new BigDecimal("139.99"), 50, null);

        mockMvc.perform(post("/api/v1/admin/variants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * POST /api/v1/admin/variants
     * Should return 409 when the SKU already exists.
     */
    @Test
    void createVariant_shouldReturn409WhenDuplicate() throws Exception {
        VariantRequest request = new VariantRequest(productId, "SKU-VAR-001", null, 50, null);
        when(variantService.createVariant(any()))
                .thenThrow(DuplicateResourceException.of("Variant", "sku", "SKU-VAR-001"));

        mockMvc.perform(post("/api/v1/admin/variants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * PUT /api/v1/admin/variants/{id}
     * Should return 200 and the updated variant when the request is valid.
     */
    @Test
    void updateVariant_shouldReturn200WhenValid() throws Exception {
        VariantRequest request = new VariantRequest(productId, "SKU-VAR-001", new BigDecimal("149.99"), 40, null);
        when(variantService.updateVariant(eq(variantId), any())).thenReturn(sampleVariant());

        mockMvc.perform(put("/api/v1/admin/variants/" + variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * PUT /api/v1/admin/variants/{id}
     * Should return 404 when the variant does not exist.
     */
    @Test
    void updateVariant_shouldReturn404WhenNotFound() throws Exception {
        VariantRequest request = new VariantRequest(productId, "SKU-VAR-001", null, 50, null);
        when(variantService.updateVariant(eq(variantId), any()))
                .thenThrow(ResourceNotFoundException.of("Variant", variantId));

        mockMvc.perform(put("/api/v1/admin/variants/" + variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * DELETE /api/v1/admin/variants/{id}
     * Should return 200 when the variant is successfully deleted.
     */
    @Test
    void deleteVariant_shouldReturn200WhenFound() throws Exception {
        doNothing().when(variantService).deleteVariant(variantId);

        mockMvc.perform(delete("/api/v1/admin/variants/" + variantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * DELETE /api/v1/admin/variants/{id}
     * Should return 404 when the variant does not exist.
     */
    @Test
    void deleteVariant_shouldReturn404WhenNotFound() throws Exception {
        doThrow(ResourceNotFoundException.of("Variant", variantId))
                .when(variantService).deleteVariant(variantId);

        mockMvc.perform(delete("/api/v1/admin/variants/" + variantId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
