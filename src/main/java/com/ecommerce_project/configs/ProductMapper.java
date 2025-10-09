package com.ecommerce_project.configs;

import com.ecommerce_project.dtos.product.ProductRequestDTO;
import com.ecommerce_project.dtos.product.ProductResponseDTO;
import com.ecommerce_project.dtos.product.ProductUpdateRequestDTO;
import com.ecommerce_project.models.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true) // remove after passing the image in the request
    @Mapping(target = "specialPrice", ignore = true)
    Product toEntity(ProductRequestDTO productRequestDTO);
    ProductResponseDTO toDTO(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductUpdateRequestDTO dto, @MappingTarget Product product);
}
