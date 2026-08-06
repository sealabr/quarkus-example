package com.example.spring;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;

/**
 * Endpoint REST com Spring MVC.
 *
 * Equivalente Quarkus: {@code GreetingResource}
 */
@RestController
@RequestMapping("/api/greetings")
@Validated
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/{name}")
    public GreetingResponse greet(@PathVariable @NotBlank String name) {
        return greetingService.greet(name);
    }
}
