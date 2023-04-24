package com.example.ordersService.service;

import com.example.ordersService.domain.Offer;
import com.example.ordersService.domain.OfferCode;
import com.example.ordersService.domain.Order;
import com.example.ordersService.domain.Price;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.repository.OfferRepository;
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
    private final OfferRepository offerRepository;

    public PriceEngineService(PriceRepository priceRepository, OfferRepository offerRepository) {
        this.priceRepository = priceRepository;
        this.offerRepository = offerRepository;
    }

    public Double calculate(Order order) throws PriceNotFoundException {
        if (isValid(order)) {
            double result = 0d;

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
                        Offer offer = offerOpt.get();
                        OfferCode offerCode = offer.getCode();
                        switch (offerCode) {
                            case BOGOF -> {
                                int modulus = quantity % 2;
                                if (modulus == 0) {
                                    result = result + unitPrice * (quantity / 2);
                                } else {
                                    int integerPart = quantity / 2;
                                    result = result + (integerPart + modulus) * unitPrice;
                                }
                            }
                            case THREE4TWO -> {
                                int modulus = quantity % 3;
                                if (modulus == 0) {
                                    result = result + unitPrice * ((quantity * 2) / 3);
                                } else {
                                    int integerPart = quantity / 3;
                                    result = result + ((integerPart * 2) + modulus) * unitPrice;
                                }
                            }
                        }
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
