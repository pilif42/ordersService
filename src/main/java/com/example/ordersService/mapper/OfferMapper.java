package com.example.ordersService.mapper;

import com.example.ordersService.domain.Offer;
import com.example.ordersService.domain.Product;
import com.example.ordersService.dto.OfferDto;
import com.example.ordersService.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    Offer offerDtoToOffer(OfferDto dto);
    OfferDto offerToOfferDto(Offer entity);

    List<OfferDto> map(List<Offer> offers);
}
