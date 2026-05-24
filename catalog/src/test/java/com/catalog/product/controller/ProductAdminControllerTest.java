package com.catalog.product.controller;

import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.product.dto.ProductRequest;
import com.catalog.product.dto.ProductResponse;
import com.catalog.product.enums.ProductStatus;
import com.catalog.product.service.ProductService;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link ProductAdminController} (admin-only endpoints).
 */
@ExtendWith(MockitoExtension.class)
class ProductAdminControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductAdminController(productService))
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
     * GET /api/v1/admin/products
     * Should return 200 with a list of all products.
     */
    @Test
    void getAllProducts_shouldReturn200WithList() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(sampleProduct()));

        mockMvc.perform(get("/api/v1/admin/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Air Max 90"));
    }

    /**
     * POST /api/v1/admin/products
     * Should return 201 and the created product when the request is valid.
     */
    @Test
    void createProduct_shouldReturn201WhenValid() throws Exception {
        ProductRequest request = new ProductRequest(
                "Air Max 90", "Classic sneaker", "SKU-001",
                new BigDecimal("129.99"), null, null, null
        );
        when(productService.createProduct(any())).thenReturn(sampleProduct());

        mockMvc.perform(post("/api/v1/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Air Max 90"));
    }

    /**
     * POST /api/v1/admin/products
     * Should return 400 when the product name is blank.
     */
    @Test
    void createProduct_shouldReturn400WhenNameIsBlank() throws Exception {
        ProductRequest request = new ProductRequest(
                "", "Classic sneaker", "SKU-001",
                new BigDecimal("129.99"), null, null, null
        );

        mockMvc.perform(post("/api/v1/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * POST /api/v1/admin/products
     * Should return 400 when the SKU is blank.
     */
    @Test
    void createProduct_shouldReturn400WhenSkuIsBlank() throws Exception {
        ProductRequest request = new ProductRequest(
                "Air Max 90", "Classic sneaker", "",
                new BigDecimal("129.99"), null, null, null
        );

        mockMvc.perform(post("/api/v1/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * POST /api/v1/admin/products
     * Should return 409 when the SKU already exists.
     */
    @Test
    void createProduct_shouldReturn409WhenDuplicate() throws Exception {
        ProductRequest request = new ProductRequest(
                "Air Max 90", null, "SKU-001",
                new BigDecimal("129.99"), null, null, null
        );
        when(productService.createProduct(any()))
                .thenThrow(DuplicateResourceException.of("Product", "sku", "SKU-001"));

        mockMvc.perform(post("/api/v1/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * PUT /api/v1/admin/products/{id}
     * Should return 200 and the updated product when the request is valid.
     */
    @Test
    void updateProduct_shouldReturn200WhenValid() throws Exception {
        UUID id = UUID.randomUUID();
        ProductRequest request = new ProductRequest(
                "Air Max 90 Updated", null, "SKU-001",
                new BigDecimal("149.99"), null, null, null
        );
        when(productService.updateProduct(eq(id), any())).thenReturn(sampleProduct());

        mockMvc.perform(put("/api/v1/admin/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * PUT /api/v1/admin/products/{id}
     * Should return 404 when the product does not exist.
     */
    @Test
    void updateProduct_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        ProductRequest request = new ProductRequest(
                "Air Max 90", null, "SKU-001",
                new BigDecimal("129.99"), null, null, null
        );
        when(productService.updateProduct(eq(id), any()))
                .thenThrow(ResourceNotFoundException.of("Product", id));

        mockMvc.perform(put("/api/v1/admin/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * DELETE /api/v1/admin/products/{id}
     * Should return 200 when the product is successfully deleted.
     */
    @Test
    void deleteProduct_shouldReturn200WhenFound() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/api/v1/admin/products/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * DELETE /api/v1/admin/products/{id}
     * Should return 404 when the product does not exist.
     */
    @Test
    void deleteProduct_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(ResourceNotFoundException.of("Product", id))
                .when(productService).deleteProduct(id);

        mockMvc.perform(delete("/api/v1/admin/products/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
