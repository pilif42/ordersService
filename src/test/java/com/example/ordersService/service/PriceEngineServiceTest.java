package com.example.ordersService.service;

import com.example.ordersService.domain.Order;
import com.example.ordersService.domain.Price;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.repository.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceEngineServiceTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceEngineService priceEngineService;

    @Nested
    @DisplayName("Tests for calculate")
    class calculate {
        @Test
        public void givenNullOrder_expectIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> priceEngineService.calculate(null));
        }

        @Test
        public void givenOrderWithNullMap_expectIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> priceEngineService.calculate(new Order()));
        }

        @Test
        public void givenOrderWithEmptyMap_expectIllegalArgumentException() {
            Order order = new Order();
            order.setProductCodeQuantityMap(new HashMap<>());
            assertThrows(IllegalArgumentException.class, () -> priceEngineService.calculate(order));
        }

        @Test
        public void givenOrderWithMapContainingProductWithNoDefinedPrice_expectPriceNotFoundException() {
            Order order = new Order();
            Map<String, Integer> productCodeQuantityMap = new HashMap<>();
            productCodeQuantityMap.put("notPricedProductCode", 3);
            order.setProductCodeQuantityMap(productCodeQuantityMap);

            Exception exception = assertThrows(PriceNotFoundException.class, () -> priceEngineService.calculate(order));
            assertEquals("No unit price found for product notPricedProductCode", exception.getMessage());
        }

        @Test
        public void givenCallToDbThrowsException_expectException() {
            final String errorMsg = "some issue with DB";
            when(priceRepository.findByProductCode(anyString())).thenThrow(new RuntimeException(errorMsg));

            Order order = new Order();
            Map<String, Integer> productCodeQuantityMap = new HashMap<>();
            productCodeQuantityMap.put("someProductCode", 3);
            order.setProductCodeQuantityMap(productCodeQuantityMap);

            Exception exception = assertThrows(RuntimeException.class, () -> priceEngineService.calculate(order));
            assertEquals(errorMsg, exception.getMessage());
        }

        @Test
        public void givenAllProductCodesHaveAPrice_expectCorrectAmount() throws PriceNotFoundException {
            final String appleCode = "FRUIT_APPLE";
            final Price applePrice = new Price();
            applePrice.setUnitPriceInCents(60);
            when(priceRepository.findByProductCode(appleCode)).thenReturn(Optional.of(applePrice));

            final String orangeCode = "FRUIT_ORANGE";
            final Price orangePrice = new Price();
            orangePrice.setUnitPriceInCents(25);
            when(priceRepository.findByProductCode(orangeCode)).thenReturn(Optional.of(orangePrice));

            Order order = new Order();
            Map<String, Integer> productCodeQuantityMap = new HashMap<>();
            productCodeQuantityMap.put(appleCode, 3);
            productCodeQuantityMap.put(orangeCode, 4);
            order.setProductCodeQuantityMap(productCodeQuantityMap);

            assertEquals(280d, priceEngineService.calculate(order));
        }
    }
}
