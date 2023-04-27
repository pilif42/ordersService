package com.example.ordersService.service.costing.rule;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleEngine {
    private final List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    public Integer process(Expression expression) {
        Rule rule = rules
                .stream()
                .filter(r -> r.evaluate(expression))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Expression does not matches any Rule"));
        return rule.getResult();
    }
}
