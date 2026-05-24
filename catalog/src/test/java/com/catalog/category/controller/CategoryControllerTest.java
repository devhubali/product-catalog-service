package com.catalog.category.controller;

import com.catalog.category.dto.CategoryResponse;
import com.catalog.category.enums.CategoryStatus;
import com.catalog.category.service.CategoryService;
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
 * Unit tests for {@link CategoryController} (public endpoints).
 *
 * Uses MockMvc standaloneSetup to test the controller layer in isolation.
 * CategoryService is mocked via Mockito to avoid database dependencies.
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    /**
     * Initialises MockMvc with the controller under test and the global exception handler.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryController(categoryService))
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
     * GET /api/v1/categories
     * Should return 200 and a list of active categories.
     */
    @Test
    void getAllActiveCategories_shouldReturn200WithList() throws Exception {
        when(categoryService.getAllActiveCategories()).thenReturn(List.of(sampleCategory()));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Electronics"))
                .andExpect(jsonPath("$.data[0].slug").value("electronics"));
    }

    /**
     * GET /api/v1/categories
     * Should return 200 with an empty list when no active categories exist.
     */
    @Test
    void getAllActiveCategories_shouldReturn200WithEmptyList() throws Exception {
        when(categoryService.getAllActiveCategories()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * GET /api/v1/categories/{slug}
     * Should return 200 and the category when slug exists.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void getCategoryBySlug_shouldReturn200WhenFound() throws Exception {
        when(categoryService.getCategoryBySlug("electronics")).thenReturn(sampleCategory());

        mockMvc.perform(get("/api/v1/categories/electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Electronics"))
                .andExpect(jsonPath("$.data.slug").value("electronics"));
    }

    /**
     * GET /api/v1/categories/{slug}
     * Should return 404 when no category matches the slug.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    void getCategoryBySlug_shouldReturn404WhenNotFound() throws Exception {
        when(categoryService.getCategoryBySlug("unknown"))
                .thenThrow(ResourceNotFoundException.of("Category", "unknown"));

        mockMvc.perform(get("/api/v1/categories/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
