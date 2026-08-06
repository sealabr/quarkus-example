package com.example.quarkus.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record EmployeeRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String department,
        @NotNull @PastOrPresent LocalDate hireDate
) {
}
