package com.example.ordersService.service;

import com.example.ordersService.domain.Order;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PriceEngineService priceEngineService;

    public OrderService(OrderRepository orderRepository, PriceEngineService priceEngineService) {
        this.orderRepository = orderRepository;
        this.priceEngineService = priceEngineService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order create(Order order) throws PriceNotFoundException {
        order.setPriceInCents(priceEngineService.calculate(order));
        return orderRepository.save(order);
    }
}
