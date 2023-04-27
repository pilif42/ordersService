package com.example.ordersService.service.costing.rule;

public abstract class AbstractRule implements Rule {
    protected Integer result;

    @Override
    public Integer getResult() {
        return result;
    }
}
