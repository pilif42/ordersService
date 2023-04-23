package com.example.ordersService.service;

import com.example.ordersService.domain.Price;
import com.example.ordersService.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public List<Price> findAll() {
        return repository.findAll();
    }
}
