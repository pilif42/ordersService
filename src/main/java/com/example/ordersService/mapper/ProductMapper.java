package com.example.ordersService.mapper;

import com.example.ordersService.domain.Product;
import com.example.ordersService.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productDtoToProduct(ProductDto dto);
    ProductDto productToProductDto(Product entity);
}
