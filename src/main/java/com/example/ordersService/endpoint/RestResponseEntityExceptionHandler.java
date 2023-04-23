package com.example.ordersService.endpoint;

import com.example.ordersService.exception.CustomerNotFoundException;
import com.example.ordersService.exception.PriceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { CustomerNotFoundException.class })
    protected ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { PriceNotFoundException.class })
    protected ResponseEntity<Object> handlePriceNotFoundException(PriceNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Currently unable to calculate the cost of your order.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Some input parameters are invalid.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Some server side issue.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // TODO Replace this handleRuntimeException with more specific exceptions that may be thrown on a DB call and map to 500.
}
