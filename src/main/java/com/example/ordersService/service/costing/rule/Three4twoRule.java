package com.example.ordersService.service.costing.rule;

import com.example.ordersService.domain.OfferCode;
import org.springframework.stereotype.Component;

@Component
public class Three4twoRule extends AbstractRule {
    @Override
    public boolean evaluate(Expression expression) {
        boolean evalResult = false;
        if (expression.getOfferCode() == OfferCode.THREE4TWO) {
            Integer quantity = expression.getQuantity();
            Integer unitPrice = expression.getUnitPrice();
            int modulus = quantity % 3;
            if (modulus == 0) {
                result = unitPrice * ((quantity * 2) / 3);
            } else {
                int integerPart = quantity / 3;
                result = ((integerPart * 2) + modulus) * unitPrice;
            }
            evalResult = true;
        }
        return evalResult;
    }
}
