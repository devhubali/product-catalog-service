package com.catalog.brand.controller;

import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.enums.BrandStatus;
import com.catalog.brand.service.BrandService;
import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link BrandController} (public endpoints).
 *
 * Uses MockMvc standaloneSetup to test the controller layer in isolation.
 * BrandService is mocked via Mockito to avoid database dependencies.
 */
@ExtendWith(MockitoExtension.class)
class BrandControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BrandService brandService;

    /**
     * Initialises MockMvc with the controller under test and the global exception handler.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BrandController(brandService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * Helper method to build a sample BrandResponse for use in tests.
     *
     * @return a BrandResponse with preset test data
     */
    private BrandResponse sampleBrand() {
        return new BrandResponse(
                UUID.randomUUID(),
                "Nike",
                "nike",
                "Sports brand",
                "https://example.com/nike.png",
                BrandStatus.ACTIVE,
                LocalDateTime.now()
        );
    }

    /**
     * GET /api/v1/brands
     * Should return 200 and a list of active brands.
     */
    @Test
    void getAllActiveBrands_shouldReturn200WithList() throws Exception {
        when(brandService.getAllActiveBrands()).thenReturn(List.of(sampleBrand()));

        mockMvc.perform(get("/api/v1/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Nike"))
                .andExpect(jsonPath("$.data[0].slug").value("nike"));
    }

    /**
     * GET /api/v1/brands
     * Should return 200 with an empty list when no active brands exist.
     */
    @Test
    void getAllActiveBrands_shouldReturn200WithEmptyList() throws Exception {
        when(brandService.getAllActiveBrands()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * GET /api/v1/brands/{slug}
     * Should return 200 and the brand when slug exists.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void getBrandBySlug_shouldReturn200WhenFound() throws Exception {
        when(brandService.getBrandBySlug("nike")).thenReturn(sampleBrand());

        mockMvc.perform(get("/api/v1/brands/nike"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Nike"))
                .andExpect(jsonPath("$.data.slug").value("nike"));
    }

    /**
     * GET /api/v1/brands/{slug}
     * Should return 404 when no brand matches the slug.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void getBrandBySlug_shouldReturn404WhenNotFound() throws Exception {
        when(brandService.getBrandBySlug("unknown"))
                .thenThrow(ResourceNotFoundException.of("Brand", "unknown"));

        mockMvc.perform(get("/api/v1/brands/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
