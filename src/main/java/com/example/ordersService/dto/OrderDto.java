package com.example.ordersService.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderDto {
    private Integer id;
    private Integer customerId;
    private Map<String, Integer> productCodeQuantityMap;
    private Double priceInCents;
}
