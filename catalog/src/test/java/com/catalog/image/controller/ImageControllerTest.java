package com.catalog.image.controller;

import com.catalog.common.exception.GlobalExceptionHandler;
import com.catalog.image.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link ImageController}.
 */
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ImageController(imageService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * POST /api/v1/admin/images/upload
     * Should return 200 and the image URL when a valid file is uploaded.
     */
    @Test
    void upload_shouldReturn200WithUrl() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.png", "image/png", "fake-image-bytes".getBytes()
        );
        when(imageService.upload(any(), eq("general")))
                .thenReturn("/api/v1/images/general/test.png");

        mockMvc.perform(multipart("/api/v1/admin/images/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.url").value("/api/v1/images/general/test.png"));
    }

    /**
     * POST /api/v1/admin/images/upload
     * Should return 200 and the image URL when a subfolder is specified.
     */
    @Test
    void upload_shouldReturn200WithSubfolder() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "product.png", "image/png", "fake-image-bytes".getBytes()
        );
        when(imageService.upload(any(), eq("products")))
                .thenReturn("/api/v1/images/products/product.png");

        mockMvc.perform(multipart("/api/v1/admin/images/upload")
                        .file(file)
                        .param("subfolder", "products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.url").value("/api/v1/images/products/product.png"));
    }

    /**
     * DELETE /api/v1/admin/images?path=...
     * Should return 200 when the image is successfully deleted.
     */
    @Test
    void delete_shouldReturn200WhenDeleted() throws Exception {
        doNothing().when(imageService).delete("general/test.png");

        mockMvc.perform(delete("/api/v1/admin/images")
                        .param("path", "general/test.png"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * GET /api/v1/images/{subfolder}/{filename}
     * Should return 200 and the file bytes when the image exists.
     */
    @Test
    void serve_shouldReturn200WithFileContent() throws Exception {
        Resource resource = new ByteArrayResource("fake-image-bytes".getBytes()) {
            @Override
            public String getFilename() {
                return "test.png";
            }
        };
        when(imageService.load("general", "test.png")).thenReturn(resource);

        mockMvc.perform(get("/api/v1/images/general/test.png"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=\"test.png\""));
    }
}
