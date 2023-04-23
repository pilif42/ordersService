package com.example.ordersService.dto;

import com.example.ordersService.domain.OfferCode;
import lombok.Data;

@Data
public class OfferDto {
    private Integer id;
    private ProductDto product;
    private OfferCode code;
}
