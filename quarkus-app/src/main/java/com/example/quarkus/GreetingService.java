package com.example.quarkus;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Serviço com CDI ({@code @ApplicationScoped}).
 *
 * Equivalente Spring: {@code @Service}
 */
@ApplicationScoped
public class GreetingService {

    @ConfigProperty(name = "app.greeting.prefix", defaultValue = "Hello")
    String prefix;

    public GreetingResponse greet(String name) {
        return new GreetingResponse(prefix + ", " + name + "!", "quarkus");
    }
}
