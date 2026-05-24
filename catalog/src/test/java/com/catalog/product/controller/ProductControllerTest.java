package com.catalog.product.controller;

import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.product.dto.ProductResponse;
import com.catalog.product.enums.ProductStatus;
import com.catalog.product.service.ProductService;
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
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link ProductController} (public endpoints).
 */
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private ProductResponse sampleProduct() {
        return new ProductResponse(
                UUID.randomUUID(),
                "Air Max 90",
                "air-max-90",
                "Classic Nike sneaker",
                "SKU-001",
                new BigDecimal("129.99"),
                "https://example.com/airmax.png",
                ProductStatus.ACTIVE,
                UUID.randomUUID(),
                "Nike",
                UUID.randomUUID(),
                "Footwear",
                LocalDateTime.now()
        );
    }

    /**
     * GET /api/v1/products
     * Should return 200 with a list of active products.
     */
    @Test
    void getAllActiveProducts_shouldReturn200WithList() throws Exception {
        when(productService.getAllActiveProducts()).thenReturn(List.of(sampleProduct()));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Air Max 90"))
                .andExpect(jsonPath("$.data[0].slug").value("air-max-90"));
    }

    /**
     * GET /api/v1/products
     * Should return 200 with an empty list when no active products exist.
     */
    @Test
    void getAllActiveProducts_shouldReturn200WithEmptyList() throws Exception {
        when(productService.getAllActiveProducts()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * GET /api/v1/products/{slug}
     * Should return 200 and the product when the slug exists.
     */
    @Test
    void getProductBySlug_shouldReturn200WhenFound() throws Exception {
        when(productService.getProductBySlug("air-max-90")).thenReturn(sampleProduct());

        mockMvc.perform(get("/api/v1/products/air-max-90"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Air Max 90"))
                .andExpect(jsonPath("$.data.sku").value("SKU-001"));
    }

    /**
     * GET /api/v1/products/{slug}
     * Should return 404 when no product matches the slug.
     */
    @Test
    void getProductBySlug_shouldReturn404WhenNotFound() throws Exception {
        when(productService.getProductBySlug("unknown"))
                .thenThrow(ResourceNotFoundException.of("Product", "unknown"));

        mockMvc.perform(get("/api/v1/products/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
