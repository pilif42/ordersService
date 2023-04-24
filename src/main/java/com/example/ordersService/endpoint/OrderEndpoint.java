package com.example.ordersService.endpoint;

import com.example.ordersService.domain.Order;
import com.example.ordersService.dto.OrderDto;
import com.example.ordersService.exception.OrderNotFoundException;
import com.example.ordersService.mapper.OrderMapper;
import com.example.ordersService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@RestController
public class OrderEndpoint {
    private static final String ORDER_NOT_FOUND_MSG = "Order with id %d does not exist.";

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

    @GetMapping(value = "/orders/{orderId}")
    public OrderDto findOrder(@PathVariable("orderId") Integer orderId) throws OrderNotFoundException {
        log.info("Entering findOrder");
        Optional<Order> orderOpt = orderService.findById(orderId);
        if (!orderOpt.isPresent()) {
            String errorMsg = format(ORDER_NOT_FOUND_MSG, orderId);
            log.error(errorMsg);
            throw new OrderNotFoundException(errorMsg);
        } else {
            return orderMapper.orderToOrderDto(orderOpt.get());
        }
    }
}
