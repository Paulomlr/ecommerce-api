package com.ecommerce_project.services;

import com.ecommerce_project.configs.CategoryMapper;
import com.ecommerce_project.dtos.CategoryRequestDTO;
import com.ecommerce_project.dtos.CategoryResponseDTO;
import com.ecommerce_project.exceptions.ApiException;
import com.ecommerce_project.models.Category;
import com.ecommerce_project.repositories.CategoryRepository;
import com.ecommerce_project.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    @DisplayName("Must save category when does not exist")
    void mustSaveCategoryWhenDoesNotExist() {
        //Arrange
        CategoryRequestDTO input = new CategoryRequestDTO("Travel");
        Category saved = new Category(1L, "TRAVEL");
        Category entity = new Category(null, "TRAVEL");
        CategoryResponseDTO expectedDto = new CategoryResponseDTO(saved.getCategoryId(), saved.getCategoryName());

        when(repository.existsByCategoryName(input.categoryName())).thenReturn(false);
        when(mapper.toEntity(input)).thenReturn(entity);
        when(mapper.toDTO(saved)).thenReturn(expectedDto);
        when(repository.save(any(Category.class))).thenReturn(saved);

        //Act
        CategoryResponseDTO result = service.createCategory(input);

        //Assert
        assertNotNull(result.categoryId());
        assertEquals("TRAVEL", result.categoryName());
        verify(repository).existsByCategoryName(input.categoryName());
    }

    @Test
    @DisplayName("Must throw exception when category already exists")
    void mustThrowExceptionWhenCategoryAlreadyExists() {
        //Arrange
        CategoryRequestDTO input = new CategoryRequestDTO("Travel");
        when(repository.existsByCategoryName(input.categoryName())).thenReturn(true);

        //Act & Assert
        assertThrows(ApiException.class, () -> service.createCategory(input));
        verify(repository).existsByCategoryName(input.categoryName());
    }

    @Test
    @DisplayName("Must update category when name does not exist")
    void mustUpdateCategoryWhenNameDoesNotExist() {
        //Arrange
        CategoryRequestDTO input = new CategoryRequestDTO("Electronics");
        Category saved = new Category(1L, "TRAVEL");
        Category categoryUpdate = new Category(1L, "ELECTRONICS");
        CategoryResponseDTO toDTO =
                new CategoryResponseDTO(categoryUpdate.getCategoryId(), categoryUpdate.getCategoryName());

        when(repository.findById(saved.getCategoryId())).thenReturn(Optional.of(saved));
        when(repository.existsByCategoryName(input.categoryName())).thenReturn(false);
        when(repository.save(any(Category.class))).thenReturn(categoryUpdate);
        when(mapper.toDTO(categoryUpdate)).thenReturn(toDTO);

        //Act
        CategoryResponseDTO result = service.updateCategory(input, saved.getCategoryId());

        //Assert
        assertEquals(result.categoryName(), input.categoryName());
        verify(repository).existsByCategoryName(input.categoryName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingCategoryWithAlreadyExistingName() {
        //Arrange
        CategoryRequestDTO input = new CategoryRequestDTO("Travel");
        Category saved = new Category(1L, "TRAVEL");

        when(repository.findById(saved.getCategoryId())).thenReturn(Optional.of(saved));
        when(repository.existsByCategoryName(input.categoryName())).thenReturn(true);

        //Act & Assert
        assertThrows(ApiException.class, () -> service.updateCategory(input, saved.getCategoryId()));
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }
}
