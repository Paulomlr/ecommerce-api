package com.ecommerce_project.configs;

import com.ecommerce_project.dtos.CategoryRequestDTO;
import com.ecommerce_project.dtos.CategoryResponseDTO;
import com.ecommerce_project.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryId", ignore = true)
    Category toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO toDTO(Category category);
}
