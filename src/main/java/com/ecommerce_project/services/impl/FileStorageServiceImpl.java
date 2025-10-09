package com.ecommerce_project.services.impl;

import com.ecommerce_project.exceptions.FileStorageException;
import com.ecommerce_project.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String save(String path, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if(!folder.exists()) folder.mkdir();

        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            throw new FileStorageException("Error saving product image", e);
        }

        return fileName;
    }
}
