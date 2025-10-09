package com.ecommerce_project.configs;

import com.ecommerce_project.dtos.category.CategoryRequestDTO;
import com.ecommerce_project.dtos.category.CategoryResponseDTO;
import com.ecommerce_project.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryId", ignore = true)
    Category toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO toDTO(Category category);
}
