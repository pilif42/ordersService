package com.example.ordersService.mapper;

import com.example.ordersService.domain.Order;
import com.example.ordersService.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderDtoToOrder(OrderDto dto);

    @Mapping(target="customerId", source="entity.customer.id")
    OrderDto orderToOrderDto(Order entity);

    List<OrderDto> map(List<Order> orders);
}
