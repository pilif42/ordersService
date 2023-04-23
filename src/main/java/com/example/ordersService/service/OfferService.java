package com.example.ordersService.service;

import com.example.ordersService.domain.Offer;
import com.example.ordersService.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }
}
