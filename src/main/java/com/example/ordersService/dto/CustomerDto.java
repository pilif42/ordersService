package com.example.ordersService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {
    private Integer id;

    @NotBlank(message = "The emailAddress is required.")
    private String emailAddress;
}
