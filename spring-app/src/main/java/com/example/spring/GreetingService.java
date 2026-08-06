package com.example.spring;

import org.springframework.stereotype.Service;

/**
 * Serviço com DI do Spring ({@code @Service}).
 *
 * Equivalente Quarkus: {@code @ApplicationScoped}
 */
@Service
public class GreetingService {

    private final GreetingProperties properties;

    public GreetingService(GreetingProperties properties) {
        this.properties = properties;
    }

    public GreetingResponse greet(String name) {
        return new GreetingResponse(properties.prefix() + ", " + name + "!", "spring");
    }
}
