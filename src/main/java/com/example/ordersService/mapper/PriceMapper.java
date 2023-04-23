package com.example.ordersService.mapper;

import com.example.ordersService.domain.Price;
import com.example.ordersService.domain.Product;
import com.example.ordersService.dto.PriceDto;
import com.example.ordersService.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    Price priceDtoToPrice(PriceDto dto);
    PriceDto priceToPriceDto(Price entity);

    List<PriceDto> map(List<Price> prices);
}
