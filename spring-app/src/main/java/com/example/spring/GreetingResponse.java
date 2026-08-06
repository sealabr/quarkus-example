package com.example.spring;

/**
 * Record JSON de resposta (Java 16+).
 * Com spring-boot-starter-web / Jackson, serializa automaticamente.
 */
public record GreetingResponse(String message, String framework) {
}
