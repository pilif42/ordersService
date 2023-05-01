package com.example.ordersService.hateoasprocessor;

import com.example.ordersService.dto.OfferDto;
import com.example.ordersService.endpoint.ProductEndpoint;
import com.example.ordersService.exception.ProductNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ProductProcessor implements RepresentationModelProcessor<EntityModel<OfferDto>> {
    @Override
    public EntityModel<OfferDto> process(EntityModel<OfferDto> model) {
        try {
            model.add(linkTo(methodOn(ProductEndpoint.class).findOne(model.getContent().getProduct().getId())).withRel("products"));
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        return model;
    }
}
