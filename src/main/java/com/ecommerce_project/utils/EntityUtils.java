package com.ecommerce_project.utils;

import com.ecommerce_project.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityUtils {
    private EntityUtils() {
        throw new IllegalStateException("Utility class"); // throw exception later in ExceptionHandler
    }

    public static <T> T findOrThrow(Long id, JpaRepository<T, Long> repository, String resourceName) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, id));
    }
}
