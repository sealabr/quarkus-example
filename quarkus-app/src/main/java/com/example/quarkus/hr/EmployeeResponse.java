package com.example.quarkus.hr;

import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String name,
        String email,
        String department,
        LocalDate hireDate,
        boolean active
) {
    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.id,
                employee.name,
                employee.email,
                employee.department,
                employee.hireDate,
                employee.active
        );
    }
}
