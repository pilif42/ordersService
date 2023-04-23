package com.example.ordersService.dto;

import lombok.Data;

@Data
public class PriceDto {
    private Integer id;
    private ProductDto product;
    private Integer unitPriceInCents;
}
