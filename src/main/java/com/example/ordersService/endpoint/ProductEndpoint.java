package com.example.ordersService.endpoint;

import com.example.ordersService.dto.ProductDto;
import com.example.ordersService.mapper.ProductMapper;
import com.example.ordersService.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ProductEndpoint {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductEndpoint(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/products")
    public List<ProductDto> findAll() {
        log.info("Entering findAll");
        return productMapper.map(productService.findAll());
    }
}
