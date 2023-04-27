package com.example.ordersService.service;

import com.example.ordersService.domain.Order;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.repository.OrderRepository;
import com.example.ordersService.service.costing.CostingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CostingService costingService;

    public OrderService(OrderRepository orderRepository, CostingService costingService) {
        this.orderRepository = orderRepository;
        this.costingService = costingService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> findAllByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order create(Order order) throws PriceNotFoundException {
        order.setPriceInCents(costingService.calculate(order));
        return orderRepository.save(order);
    }
}
