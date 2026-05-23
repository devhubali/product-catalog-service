package com.catalog.storage;

import com.catalog.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

/**
 * Handles file uploads and retrievals to/from the local filesystem.
 *
 * Files are stored under the configured upload directory.
 * Each file is renamed to a UUID to avoid naming conflicts.
 *
 * Example usage:
 * String path = fileStorageService.storeFile(file, "products");
 * Result: uploads/products/550e8400-e29b-41d4-a716-446655440000.jpg
 */
@Service
public class FileStorageService {

    private final Path uploadRoot;

    public FileStorageService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadRoot);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + uploadDir, e);
        }
    }

    /**
     * Saves the uploaded file to disk under a subfolder.
     *
     * The file is renamed to a random UUID to prevent naming collisions.
     * Returns the relative path that can be stored in the database.
     */
    public String storeFile(MultipartFile file, String subfolder) {
        String originalFilename = StringUtils.cleanPath(
                Objects.requireNonNull(file.getOriginalFilename(), "Filename must not be null")
        );

        String extension = originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        String newFilename = UUID.randomUUID() + extension;

        try {
            Path targetDir = this.uploadRoot.resolve(subfolder);
            Files.createDirectories(targetDir);

            Path targetPath = targetDir.resolve(newFilename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return subfolder + "/" + newFilename;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file: " + originalFilename, e);
        }
    }

    /**
     * Deletes a file from disk using its stored relative path.
     */
    public void deleteFile(String filePath) {
        try {
            Path target = this.uploadRoot.resolve(filePath).normalize();
            Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + filePath, e);
        }
    }

    /**
     * Loads a file as a Spring Resource so it can be served over HTTP.
     */
    public Resource loadFileAsResource(String filePath) {
        try {
            Path target = this.uploadRoot.resolve(filePath).normalize();
            Resource resource = new UrlResource(target.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new ResourceNotFoundException("File not found: " + filePath);
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File not found: " + filePath);
        }
    }
}
