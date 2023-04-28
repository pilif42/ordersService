package com.example.ordersService.endpoint;

import com.example.ordersService.dto.GreetingDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("greeting")
public class GreetingEndpoint {
    private static final String TEMPLATE = "Hello, %s!";

    @GetMapping
    public HttpEntity<GreetingDto> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        GreetingDto greeting = new GreetingDto(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingEndpoint.class).greeting(name)).withSelfRel());
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
