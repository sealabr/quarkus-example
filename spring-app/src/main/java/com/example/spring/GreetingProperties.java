package com.example.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bind tipado de configuração.
 *
 * Equivalente Quarkus: {@code @ConfigProperty} / {@code @ConfigMapping}
 */
@ConfigurationProperties(prefix = "app.greeting")
public record GreetingProperties(String prefix) {
}
