package com.example.ordersService.endpoint;

import com.example.ordersService.dto.OrderDto;
import com.example.ordersService.mapper.OrderMapper;
import com.example.ordersService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OrderEndpoint {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderEndpoint(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/orders")
    public List<OrderDto> findAll() {
        log.info("Entering findAll");
        return orderMapper.map(orderService.findAll());
    }
}
