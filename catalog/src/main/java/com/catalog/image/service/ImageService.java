package com.catalog.image.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile file, String subfolder);

    void delete(String filePath);

    Resource load(String subfolder, String filename);
}
