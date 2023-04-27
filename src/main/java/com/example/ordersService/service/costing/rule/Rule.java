package com.example.ordersService.service.costing.rule;

public interface Rule {
    boolean evaluate(Expression expression);
    Integer getResult();
}
