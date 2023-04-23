package com.example.ordersService.service;

import com.example.ordersService.domain.Customer;
import com.example.ordersService.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findById(Integer customerId) {
        return repository.findById(customerId);
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }
}
