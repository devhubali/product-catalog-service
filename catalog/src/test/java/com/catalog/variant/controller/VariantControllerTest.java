package com.catalog.variant.controller;

import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.variant.dto.VariantResponse;
import com.catalog.variant.entity.Variant;
import com.catalog.variant.service.VariantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link VariantController} (public endpoints).
 */
@ExtendWith(MockitoExtension.class)
class VariantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VariantService variantService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new VariantController(variantService))
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
     * GET /api/v1/products/{productId}/variants
     * Should return 200 with a list of variants for a product.
     */
    @Test
    void getVariantsByProductId_shouldReturn200WithList() throws Exception {
        when(variantService.getVariantsByProductId(productId)).thenReturn(List.of(sampleVariant()));

        mockMvc.perform(get("/api/v1/products/" + productId + "/variants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].sku").value("SKU-VAR-001"));
    }

    /**
     * GET /api/v1/products/{productId}/variants
     * Should return 200 with an empty list when the product has no variants.
     */
    @Test
    void getVariantsByProductId_shouldReturn200WithEmptyList() throws Exception {
        when(variantService.getVariantsByProductId(productId)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/products/" + productId + "/variants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * GET /api/v1/products/{productId}/variants
     * Should return 404 when the product does not exist.
     */
    @Test
    void getVariantsByProductId_shouldReturn404WhenProductNotFound() throws Exception {
        when(variantService.getVariantsByProductId(productId))
                .thenThrow(ResourceNotFoundException.of("Product", productId));

        mockMvc.perform(get("/api/v1/products/" + productId + "/variants"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * GET /api/v1/products/{productId}/variants/{id}
     * Should return 200 and the variant when it exists.
     */
    @Test
    void getVariantById_shouldReturn200WhenFound() throws Exception {
        when(variantService.getVariantById(variantId)).thenReturn(sampleVariant());

        mockMvc.perform(get("/api/v1/products/" + productId + "/variants/" + variantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.sku").value("SKU-VAR-001"));
    }

    /**
     * GET /api/v1/products/{productId}/variants/{id}
     * Should return 404 when the variant does not exist.
     */
    @Test
    void getVariantById_shouldReturn404WhenNotFound() throws Exception {
        when(variantService.getVariantById(variantId))
                .thenThrow(ResourceNotFoundException.of("Variant", variantId));

        mockMvc.perform(get("/api/v1/products/" + productId + "/variants/" + variantId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
