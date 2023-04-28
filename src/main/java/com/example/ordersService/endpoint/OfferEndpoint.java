package com.example.ordersService.endpoint;

import com.example.ordersService.domain.Offer;
import com.example.ordersService.domain.Product;
import com.example.ordersService.dto.OfferDto;
import com.example.ordersService.exception.ProductNotFoundException;
import com.example.ordersService.mapper.OfferMapper;
import com.example.ordersService.service.OfferService;
import com.example.ordersService.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("offers")
public class OfferEndpoint {
    private final OfferService offerService;
    private final ProductService productService;
    private final OfferMapper offerMapper;

    public OfferEndpoint(OfferService offerService, OfferMapper offerMapper, ProductService productService) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
        this.productService = productService;
    }

    @GetMapping
    public List<OfferDto> findAll() {
        log.info("Entering findAll");
        return offerMapper.map(offerService.findAll());
    }

    @PostMapping
    public OfferDto create(@RequestBody @Valid OfferDto offerDto) throws ProductNotFoundException {
        log.info("Entering create");
        Integer productId = offerDto.getProduct().getId();
        Optional<Product> productOpt = productService.findById(productId);
        if (productOpt.isPresent()) {
            Offer offer = offerMapper.offerDtoToOffer(offerDto);
            offer.setProduct(productOpt.get());
            return offerMapper.offerToOfferDto(offerService.create(offer));
        } else {
            final String errorMsg = String.format("No product found with id %d", productId);
            log.error(errorMsg);
            throw new ProductNotFoundException(errorMsg);
        }
    }
}
