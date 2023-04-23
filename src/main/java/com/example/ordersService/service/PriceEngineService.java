package com.example.ordersService.service;

import com.example.ordersService.domain.Order;
import com.example.ordersService.domain.Price;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
public class PriceEngineService {
    private final PriceRepository priceRepository;

    public PriceEngineService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Double calculate(Order order) throws PriceNotFoundException {
        if (isValid(order)) {
            double result = 0d;

            Map<String, Integer> productCodeQuantityMap = order.getProductCodeQuantityMap();
            Set<String> productCodes = productCodeQuantityMap.keySet();
            for (String productCode : productCodes) {
                Optional<Price> priceOpt = priceRepository.findByProductCode(productCode);
                if (priceOpt.isPresent()) {
                    result = result + priceOpt.get().getUnitPriceInCents() * productCodeQuantityMap.get(productCode);
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
