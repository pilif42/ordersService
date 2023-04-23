package com.example.ordersService.repository;

import com.example.ordersService.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    Optional<Price> findByProductCode(String productCode);
}
