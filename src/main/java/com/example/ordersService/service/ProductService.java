package com.example.ordersService.service;

import com.example.ordersService.domain.Customer;
import com.example.ordersService.domain.Product;
import com.example.ordersService.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }
}
