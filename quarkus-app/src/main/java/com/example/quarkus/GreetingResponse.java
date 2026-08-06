package com.example.quarkus;

/**
 * Record JSON de resposta (Java 16+).
 * Com quarkus-rest-jackson, serializa automaticamente.
 */
public record GreetingResponse(String message, String framework) {
}
