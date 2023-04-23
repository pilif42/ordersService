package com.example.ordersService.endpoint;

import com.example.ordersService.dto.PriceDto;
import com.example.ordersService.mapper.PriceMapper;
import com.example.ordersService.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class PriceEndpoint {
    private final PriceService priceService;
    private final PriceMapper priceMapper;

    public PriceEndpoint(PriceService priceService, PriceMapper priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }

    @GetMapping("/prices")
    public List<PriceDto> findAll() {
        log.info("Entering findAll");
        return priceMapper.map(priceService.findAll());
    }
}
