package com.catalog.brand.controller;

import com.catalog.brand.dto.BrandRequest;
import com.catalog.brand.dto.BrandResponse;
import com.catalog.brand.enums.BrandStatus;
import com.catalog.brand.service.BrandService;
import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.common.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link BrandAdminController} (admin-only endpoints).
 *
 * Uses MockMvc standaloneSetup to test the controller layer in isolation.
 * BrandService is mocked via Mockito to avoid database dependencies.
 * Security enforcement is tested separately via integration tests.
 */
@ExtendWith(MockitoExtension.class)
class BrandAdminControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BrandService brandService;

    /**
     * Initialises MockMvc with the controller under test and the global exception handler.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BrandAdminController(brandService))
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
     * GET /api/v1/admin/brands
     * Should return 200 and a list of all brands when called by ADMIN.
     */
    @Test
    void getAllBrands_shouldReturn200WithList() throws Exception {
        when(brandService.getAllBrands()).thenReturn(List.of(sampleBrand()));

        mockMvc.perform(get("/api/v1/admin/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Nike"));
    }

    /**
     * POST /api/v1/admin/brands
     * Should return 201 and the created brand when request is valid.
     */
    @Test
    void createBrand_shouldReturn201WhenValid() throws Exception {
        BrandRequest request = new BrandRequest("Nike", "Sports brand", "https://example.com/nike.png");
        when(brandService.createBrand(any())).thenReturn(sampleBrand());

        mockMvc.perform(post("/api/v1/admin/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Nike"));
    }

    /**
     * POST /api/v1/admin/brands
     * Should return 400 when brand name is blank (validation failure).
     */
    @Test
    void createBrand_shouldReturn400WhenNameIsBlank() throws Exception {
        BrandRequest request = new BrandRequest("", "Sports brand", null);

        mockMvc.perform(post("/api/v1/admin/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * POST /api/v1/admin/brands
     * Should return 409 when brand name already exists (duplicate slug).
     */
    @Test
    void createBrand_shouldReturn409WhenDuplicate() throws Exception {
        BrandRequest request = new BrandRequest("Nike", null, null);
        when(brandService.createBrand(any()))
                .thenThrow(DuplicateResourceException.of("Brand", "name", "Nike"));

        mockMvc.perform(post("/api/v1/admin/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * PUT /api/v1/admin/brands/{id}
     * Should return 200 and the updated brand when request is valid.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void updateBrand_shouldReturn200WhenValid() throws Exception {
        UUID id = UUID.randomUUID();
        BrandRequest request = new BrandRequest("Nike Updated", "Updated desc", null);
        when(brandService.updateBrand(eq(id), any())).thenReturn(sampleBrand());

        mockMvc.perform(put("/api/v1/admin/brands/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * PUT /api/v1/admin/brands/{id}
     * Should return 404 when the brand does not exist.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void updateBrand_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        BrandRequest request = new BrandRequest("Nike", null, null);
        when(brandService.updateBrand(eq(id), any()))
                .thenThrow(ResourceNotFoundException.of("Brand", id));

        mockMvc.perform(put("/api/v1/admin/brands/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * DELETE /api/v1/admin/brands/{id}
     * Should return 200 when brand is successfully deleted.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void deleteBrand_shouldReturn200WhenFound() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(brandService).deleteBrand(id);

        mockMvc.perform(delete("/api/v1/admin/brands/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * DELETE /api/v1/admin/brands/{id}
     * Should return 404 when the brand does not exist.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void deleteBrand_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(ResourceNotFoundException.of("Brand", id))
                .when(brandService).deleteBrand(id);

        mockMvc.perform(delete("/api/v1/admin/brands/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

}
