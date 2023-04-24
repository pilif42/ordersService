package com.example.ordersService.repository;

import com.example.ordersService.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    Optional<Offer> findByProductCode(String productCode);
}

