package com.example.ordersService.endpoint;

import com.example.ordersService.domain.Product;
import com.example.ordersService.dto.ProductDto;
import com.example.ordersService.exception.ProductNotFoundException;
import com.example.ordersService.mapper.ProductMapper;
import com.example.ordersService.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductEndpoint {
    private static final String PRODUCT_NOT_FOUND_MSG = "Product with id %d does not exist.";

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductEndpoint(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public CollectionModel<EntityModel<ProductDto>> findAll() {
        log.info("Entering findAll");
        List<EntityModel<ProductDto>> productDtos = productMapper.map(productService.findAll()).stream().map(product -> {
                    try {
                        return EntityModel.of(product,
                                linkTo(methodOn(ProductEndpoint.class).findOne(product.getId())).withSelfRel());
                    } catch (ProductNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }) //
                .toList();

        return CollectionModel.of(productDtos, linkTo(methodOn(ProductEndpoint.class).findAll()).withSelfRel());
    }

    @GetMapping("/{productId}")
    public EntityModel<ProductDto> findOne(@PathVariable("productId") Integer productId) throws ProductNotFoundException {
        log.info("Entering findOne");
        Optional<Product> prodOpt = productService.findById(productId);
        if (prodOpt.isPresent()) {
            ProductDto productDto = productMapper.productToProductDto(prodOpt.get());
            return EntityModel.of(productDto,
                    linkTo(methodOn(ProductEndpoint.class).findOne(productId)).withSelfRel(),
                    linkTo(methodOn(ProductEndpoint.class).findAll()).withRel("products"));
        } else {
            String errorMsg = format(PRODUCT_NOT_FOUND_MSG, productId);
            log.error(errorMsg);
            throw new ProductNotFoundException(errorMsg);
        }
    }
}
