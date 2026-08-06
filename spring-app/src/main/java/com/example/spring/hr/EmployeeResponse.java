package com.example.spring.hr;

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
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getHireDate(),
                employee.isActive()
        );
    }
}
