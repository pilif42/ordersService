package com.example.ordersService.service.costing;

import com.example.ordersService.domain.Offer;
import com.example.ordersService.domain.Order;
import com.example.ordersService.domain.Price;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.repository.OfferRepository;
import com.example.ordersService.repository.PriceRepository;
import com.example.ordersService.service.costing.rule.Expression;
import com.example.ordersService.service.costing.rule.RuleEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
public class CostingService {
    private final PriceRepository priceRepository;
    private final OfferRepository offerRepository;
    private final RuleEngine ruleEngine;

    public CostingService(PriceRepository priceRepository, OfferRepository offerRepository, RuleEngine ruleEngine) {
        this.priceRepository = priceRepository;
        this.offerRepository = offerRepository;
        this.ruleEngine = ruleEngine;
    }

    public int calculate(Order order) throws PriceNotFoundException {
        if (isValid(order)) {
            int result = 0;

            Map<String, Integer> productCodeQuantityMap = order.getProductCodeQuantityMap();
            Set<String> productCodes = productCodeQuantityMap.keySet();
            for (String productCode : productCodes) {
                Optional<Price> priceOpt = priceRepository.findByProductCode(productCode);
                if (priceOpt.isPresent()) {
                    final Integer quantity = productCodeQuantityMap.get(productCode);
                    final Integer unitPrice = priceOpt.get().getUnitPriceInCents();

                    Optional<Offer> offerOpt = offerRepository.findByProductCode(productCode);
                    if (offerOpt.isEmpty()) {
                        result = result + unitPrice * quantity;
                    } else {
                        Expression expression = new Expression();
                        expression.setOfferCode(offerOpt.get().getCode());
                        expression.setQuantity(quantity);
                        expression.setUnitPrice(unitPrice);
                        result = result + ruleEngine.process(expression);
                    }
                } else {
                    String errorMsg = format("No unit price found for product %s", productCode);
                    log.error(errorMsg);
                    throw new PriceNotFoundException(errorMsg);
                }
            }

            return result;
        } else {
            String errorMsg = "Invalid order: " + order;
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    private boolean isValid(Order order) {
        boolean result = false;
        if (order != null) {
            Map<String, Integer> productCodeQuantityMap = order.getProductCodeQuantityMap();
            if (productCodeQuantityMap != null && !productCodeQuantityMap.isEmpty()) {
                result = true;
            }
        }
        return result;
    }
}
