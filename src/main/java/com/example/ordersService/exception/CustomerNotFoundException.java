package com.example.ordersService.exception;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
