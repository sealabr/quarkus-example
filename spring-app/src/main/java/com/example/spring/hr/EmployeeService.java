package com.example.spring.hr;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeResponse> list(String department, Boolean active) {
        List<Employee> employees;
        if (department != null && !department.isBlank() && active != null) {
            employees = repository.findByDepartmentIgnoreCaseAndActive(department, active);
        } else if (department != null && !department.isBlank()) {
            employees = repository.findByDepartmentIgnoreCase(department);
        } else if (active != null) {
            employees = repository.findByActive(active);
        } else {
            employees = repository.findAllByOrderByNameAsc();
        }
        return employees.stream().map(EmployeeResponse::from).toList();
    }

    public EmployeeResponse get(Long id) {
        return EmployeeResponse.from(findOrThrow(id));
    }

    @Transactional
    public EmployeeResponse hire(EmployeeRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw conflict("Já existe funcionário com o e-mail: " + request.email());
        }

        Employee employee = new Employee(
                request.name(),
                request.email(),
                request.department(),
                request.hireDate(),
                true
        );
        return EmployeeResponse.from(repository.save(employee));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = findOrThrow(id);
        if (!employee.isActive()) {
            throw conflict("Funcionário inativo não pode ser atualizado. Reative antes.");
        }

        repository.findByEmail(request.email())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw conflict("Já existe funcionário com o e-mail: " + request.email());
                });

        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setDepartment(request.department());
        employee.setHireDate(request.hireDate());
        return EmployeeResponse.from(employee);
    }

    @Transactional
    public EmployeeResponse deactivate(Long id) {
        Employee employee = findOrThrow(id);
        if (!employee.isActive()) {
            throw conflict("Funcionário já está inativo.");
        }
        employee.setActive(false);
        return EmployeeResponse.from(employee);
    }

    public List<DepartmentCount> activeByDepartment() {
        return repository.countActiveByDepartment();
    }

    private Employee findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Funcionário não encontrado: " + id));
    }

    private static ResponseStatusException conflict(String message) {
        return new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
}
