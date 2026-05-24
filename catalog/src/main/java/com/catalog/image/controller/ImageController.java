package com.catalog.image.controller;

import com.catalog.common.response.ApiResponse;
import com.catalog.image.dto.ImageUploadResponse;
import com.catalog.image.service.ImageService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/api/v1/admin/images/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ImageUploadResponse>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "subfolder", defaultValue = "general") String subfolder
    ) {
        String url = imageService.upload(file, subfolder);
        return ResponseEntity.ok(ApiResponse.success(new ImageUploadResponse(url)));
    }

    @DeleteMapping("/api/v1/admin/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @RequestParam("path") @NotBlank String filePath
    ) {
        imageService.delete(filePath);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/api/v1/images/{subfolder}/{filename}")
    public ResponseEntity<Resource> serve(
            @PathVariable String subfolder,
            @PathVariable String filename
    ) {
        Resource resource = imageService.load(subfolder, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
