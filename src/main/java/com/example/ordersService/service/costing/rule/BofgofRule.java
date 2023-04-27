package com.example.ordersService.service.costing.rule;

import com.example.ordersService.domain.OfferCode;
import org.springframework.stereotype.Component;

@Component
public class BofgofRule extends AbstractRule {
    @Override
    public boolean evaluate(Expression expression) {
        boolean evalResult = false;
        if (expression.getOfferCode() == OfferCode.BOGOF) {
            Integer quantity = expression.getQuantity();
            Integer unitPrice = expression.getUnitPrice();
            int modulus = quantity % 2;
            if (modulus == 0) {
                result = unitPrice * (quantity / 2);
            } else {
                int integerPart = quantity / 2;
                result = (integerPart + modulus) * unitPrice;
            }
            evalResult = true;
        }
        return evalResult;
    }


}
