package com.ecommerce_project.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String save(String path, MultipartFile file);
}
