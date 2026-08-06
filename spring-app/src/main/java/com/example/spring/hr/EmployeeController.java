package com.example.spring.hr;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * API REST de RH — mesmo contrato do Quarkus {@code EmployeeResource}.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeResponse> list(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean active) {
        return service.list(department, active);
    }

    @GetMapping("/stats/by-department")
    public List<DepartmentCount> byDepartment() {
        return service.activeByDepartment();
    }

    @GetMapping("/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> hire(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse created = service.hire(request);
        return ResponseEntity.created(URI.create("/api/employees/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        return service.update(id, request);
    }

    @PostMapping("/{id}/deactivate")
    public EmployeeResponse deactivate(@PathVariable Long id) {
        return service.deactivate(id);
    }
}
