package com.catalog.category.controller;

import com.catalog.category.dto.CategoryRequest;
import com.catalog.category.dto.CategoryResponse;
import com.catalog.category.enums.CategoryStatus;
import com.catalog.category.service.CategoryService;
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
 * Unit tests for {@link CategoryAdminController} (admin-only endpoints).
 *
 * Uses MockMvc standaloneSetup to test the controller layer in isolation.
 * CategoryService is mocked via Mockito to avoid database dependencies.
 * Security enforcement is tested separately via integration tests.
 */
@ExtendWith(MockitoExtension.class)
class CategoryAdminControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CategoryService categoryService;

    /**
     * Initialises MockMvc with the controller under test and the global exception handler.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryAdminController(categoryService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * Helper method to build a sample CategoryResponse for use in tests.
     *
     * @return a CategoryResponse with preset test data
     */
    private CategoryResponse sampleCategory() {
        return new CategoryResponse(
                UUID.randomUUID(),
                "Electronics",
                "electronics",
                "Electronic devices and accessories",
                "https://example.com/electronics.png",
                CategoryStatus.ACTIVE,
                LocalDateTime.now()
        );
    }

    /**
     * GET /api/v1/admin/categories
     * Should return 200 and a list of all categories.
     */
    @Test
    void getAllCategories_shouldReturn200WithList() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(List.of(sampleCategory()));

        mockMvc.perform(get("/api/v1/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Electronics"));
    }

    /**
     * POST /api/v1/admin/categories
     * Should return 201 and the created category when request is valid.
     */
    @Test
    void createCategory_shouldReturn201WhenValid() throws Exception {
        CategoryRequest request = new CategoryRequest("Electronics", "Devices", "https://example.com/electronics.png");
        when(categoryService.createCategory(any())).thenReturn(sampleCategory());

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Electronics"));
    }

    /**
     * POST /api/v1/admin/categories
     * Should return 400 when category name is blank (validation failure).
     */
    @Test
    void createCategory_shouldReturn400WhenNameIsBlank() throws Exception {
        CategoryRequest request = new CategoryRequest("", "Devices", null);

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * POST /api/v1/admin/categories
     * Should return 409 when category name already exists (duplicate slug).
     */
    @Test
    void createCategory_shouldReturn409WhenDuplicate() throws Exception {
        CategoryRequest request = new CategoryRequest("Electronics", null, null);
        when(categoryService.createCategory(any()))
                .thenThrow(DuplicateResourceException.of("Category", "name", "Electronics"));

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * PUT /api/v1/admin/categories/{id}
     * Should return 200 and the updated category when request is valid.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void updateCategory_shouldReturn200WhenValid() throws Exception {
        UUID id = UUID.randomUUID();
        CategoryRequest request = new CategoryRequest("Electronics Updated", "Updated desc", null);
        when(categoryService.updateCategory(eq(id), any())).thenReturn(sampleCategory());

        mockMvc.perform(put("/api/v1/admin/categories/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * PUT /api/v1/admin/categories/{id}
     * Should return 404 when the category does not exist.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void updateCategory_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        CategoryRequest request = new CategoryRequest("Electronics", null, null);
        when(categoryService.updateCategory(eq(id), any()))
                .thenThrow(ResourceNotFoundException.of("Category", id));

        mockMvc.perform(put("/api/v1/admin/categories/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * DELETE /api/v1/admin/categories/{id}
     * Should return 200 when category is successfully deleted.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void deleteCategory_shouldReturn200WhenFound() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(categoryService).deleteCategory(id);

        mockMvc.perform(delete("/api/v1/admin/categories/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * DELETE /api/v1/admin/categories/{id}
     * Should return 404 when the category does not exist.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void deleteCategory_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(ResourceNotFoundException.of("Category", id))
                .when(categoryService).deleteCategory(id);

        mockMvc.perform(delete("/api/v1/admin/categories/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
