package com.example.ordersService.mapper;

import com.example.ordersService.domain.Customer;
import com.example.ordersService.dto.CustomerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer entity);

    List<CustomerDto> map(List<Customer> customers);
}
