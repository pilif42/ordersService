package com.example.ordersService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class GreetingDto extends RepresentationModel<GreetingDto> {
    private final String content;

    @JsonCreator
    public GreetingDto(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
