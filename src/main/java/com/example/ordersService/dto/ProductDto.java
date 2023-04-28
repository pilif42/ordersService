package com.example.ordersService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDto extends RepresentationModel<ProductDto> {
    private Integer id;
    private String code;
    private String description;
}
