package com.example.ordersService.service;

import com.example.ordersService.domain.Customer;
import com.example.ordersService.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService customerService;

    @Nested
    @DisplayName("Tests for findAll")
    class findAll {
        @Test
        public void givenSuccessfulCallToDb_expectListOfAllCustomers() {
            Customer customerOne = new Customer();
            customerOne.setId(1);
            Customer customerTwo = new Customer();
            customerTwo.setId(2);
            List<Customer> customerList = List.of(customerOne, customerTwo);
            when(repository.findAll()).thenReturn(customerList);

            assertEquals(customerList, customerService.findAll());
        }

        @Test
        public void givenCallToDbThrowsException_expectException() {
            final String errorMsg = "some issue with DB";
            when(repository.findAll()).thenThrow(new RuntimeException(errorMsg));

            Exception exception = assertThrows(RuntimeException.class, () -> customerService.findAll());
            assertEquals(errorMsg, exception.getMessage());
        }
    }

    // TODO Test findById & create
}
