package com.example.ordersService.endpoint;

import com.example.ordersService.domain.Offer;
import com.example.ordersService.domain.Product;
import com.example.ordersService.dto.OfferDto;
import com.example.ordersService.exception.OfferNotFoundException;
import com.example.ordersService.exception.ProductNotFoundException;
import com.example.ordersService.mapper.OfferMapper;
import com.example.ordersService.service.OfferService;
import com.example.ordersService.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * So that we do not pollute OfferEndpoint with ProductEndpoint, we do not create links to Products in here. Instead,
 * we have registered ProductProcessor in HateoasConfig.
 */
@Slf4j
@RestController
@RequestMapping("offers")
public class OfferEndpoint {
    private static final String OFFER_NOT_FOUND_MSG = "Offer with id %d does not exist.";

    private final OfferService offerService;
    private final ProductService productService;
    private final OfferMapper offerMapper;

    public OfferEndpoint(OfferService offerService, OfferMapper offerMapper, ProductService productService) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
        this.productService = productService;
    }

    @GetMapping("/{offerId}")
    public EntityModel<OfferDto> findOne(@PathVariable("offerId") Integer offerId) throws OfferNotFoundException {
        log.info("Entering findOne");
        Optional<Offer> offerOpt = offerService.findById(offerId);
        if (offerOpt.isPresent()) {
            OfferDto offerDto = offerMapper.offerToOfferDto(offerOpt.get());
            return EntityModel.of(offerDto,
                    linkTo(methodOn(OfferEndpoint.class).findOne(offerId)).withSelfRel(),
                    linkTo(methodOn(OfferEndpoint.class).findAll()).withRel("offers"));
        } else {
            String errorMsg = format(OFFER_NOT_FOUND_MSG, offerId);
            log.error(errorMsg);
            throw new OfferNotFoundException(errorMsg);
        }
    }

    @GetMapping
    public CollectionModel<EntityModel<OfferDto>> findAll() {
        log.info("Entering findAll");
        List<EntityModel<OfferDto>> offerDtos = offerMapper.map(offerService.findAll()).stream().map(offer -> {
                    try {
                        return EntityModel.of(offer,
                                linkTo(methodOn(OfferEndpoint.class).findOne(offer.getId())).withSelfRel());
                    } catch (OfferNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }) //
                .toList();
        return CollectionModel.of(offerDtos, linkTo(methodOn(OfferEndpoint.class).findAll()).withSelfRel());
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
