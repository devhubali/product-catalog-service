package com.catalog.image.service;

import com.catalog.storage.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    private final FileStorageService fileStorageService;

    public ImageServiceImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public String upload(MultipartFile file, String subfolder) {
        return fileStorageService.storeFile(file, subfolder);
    }

    @Override
    public void delete(String filePath) {
        fileStorageService.deleteFile(filePath);
    }

    @Override
    public Resource load(String subfolder, String filename) {
        return fileStorageService.loadFileAsResource(subfolder + "/" + filename);
    }
}
