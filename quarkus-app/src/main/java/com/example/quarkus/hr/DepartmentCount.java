package com.example.quarkus.hr;

/**
 * Projeção: quantos ativos por departamento.
 */
public record DepartmentCount(String department, long total) {
}
