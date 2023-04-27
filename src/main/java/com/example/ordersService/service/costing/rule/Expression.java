package com.example.ordersService.service.costing.rule;

import com.example.ordersService.domain.OfferCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class Expression {
    private OfferCode offerCode;
    private Integer quantity;
    private Integer unitPrice;
}
