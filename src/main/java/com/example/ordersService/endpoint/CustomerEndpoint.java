package com.example.ordersService.endpoint;

import com.example.ordersService.domain.Customer;
import com.example.ordersService.domain.Order;
import com.example.ordersService.dto.CustomerDto;
import com.example.ordersService.dto.OrderDto;
import com.example.ordersService.exception.CustomerNotFoundException;
import com.example.ordersService.exception.PriceNotFoundException;
import com.example.ordersService.mapper.CustomerMapper;
import com.example.ordersService.mapper.OrderMapper;
import com.example.ordersService.service.CustomerService;
import com.example.ordersService.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("customers")
public class CustomerEndpoint {
    private static final String CUSTOMER_NOT_FOUND_MSG = "Customer with id %d does not exist.";

    private final CustomerService customerService;
    private final OrderService orderService;
    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;

    public CustomerEndpoint(CustomerService customerService, OrderService orderService, CustomerMapper customerMapper,
                            OrderMapper orderMapper) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.customerMapper = customerMapper;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<CustomerDto> findAll() {
        log.info("Entering findAll");
        return customerMapper.map(customerService.findAll());
    }

    @GetMapping("/{customerId}")
    public CustomerDto findOne(@PathVariable("customerId") Integer customerId) throws CustomerNotFoundException {
        log.info("Entering findOne");
        Optional<Customer> custOpt = customerService.findById(customerId);
        if (custOpt.isPresent()) {
            return customerMapper.customerToCustomerDto(custOpt.get());
        } else {
            String errorMsg = format(CUSTOMER_NOT_FOUND_MSG, customerId);
            log.error(errorMsg);
            throw new CustomerNotFoundException(errorMsg);
        }
    }

    @PostMapping
    public CustomerDto create(@RequestBody @Valid CustomerDto customerDto) {
        log.info("Entering create");
        return customerMapper.customerToCustomerDto(customerService.create(customerMapper.customerDtoToCustomer(customerDto)));
    }

    @GetMapping(value = "/{customerId}/orders")
    public List<OrderDto> findOrders(@PathVariable("customerId") Integer customerId) {
        log.info("Entering findOrders");
        return orderMapper.map(orderService.findAllByCustomerId(customerId));
    }

    @PostMapping(value = "/{customerId}/orders")
    public OrderDto createOrder(@PathVariable("customerId") Integer customerId,
                                @RequestBody OrderDto orderDto) throws CustomerNotFoundException, PriceNotFoundException {
        log.info("Entering createOrder");
        Optional<Customer> customerOpt = customerService.findById(customerId);
        if (!customerOpt.isPresent()) {
            String errorMsg = format(CUSTOMER_NOT_FOUND_MSG, customerId);
            log.error(errorMsg);
            throw new CustomerNotFoundException(errorMsg);
        } else {
            Order partiallyDefinedOrder = orderMapper.orderDtoToOrder(orderDto);
            partiallyDefinedOrder.setCustomer(customerOpt.get());
            return orderMapper.orderToOrderDto(orderService.create(partiallyDefinedOrder));
        }
    }
}
