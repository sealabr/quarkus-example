package com.example.quarkus.hr;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Regras de negócio de RH — o Resource só fala HTTP; a lógica fica aqui.
 */
@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository repository;

    public List<EmployeeResponse> list(String department, Boolean active) {
        return repository.search(department, active).stream()
                .map(EmployeeResponse::from)
                .toList();
    }

    public EmployeeResponse get(Long id) {
        return EmployeeResponse.from(findOrThrow(id));
    }

    @Transactional
    public EmployeeResponse hire(EmployeeRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw conflict("Já existe funcionário com o e-mail: " + request.email());
        }

        Employee employee = new Employee();
        employee.name = request.name();
        employee.email = request.email();
        employee.department = request.department();
        employee.hireDate = request.hireDate();
        employee.active = true;
        repository.persist(employee);

        return EmployeeResponse.from(employee);
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = findOrThrow(id);
        if (!employee.active) {
            throw conflict("Funcionário inativo não pode ser atualizado. Reative antes.");
        }

        repository.findByEmail(request.email())
                .filter(other -> !other.id.equals(id))
                .ifPresent(other -> {
                    throw conflict("Já existe funcionário com o e-mail: " + request.email());
                });

        employee.name = request.name();
        employee.email = request.email();
        employee.department = request.department();
        employee.hireDate = request.hireDate();

        return EmployeeResponse.from(employee);
    }

    @Transactional
    public EmployeeResponse deactivate(Long id) {
        Employee employee = findOrThrow(id);
        if (!employee.active) {
            throw conflict("Funcionário já está inativo.");
        }
        employee.active = false;
        return EmployeeResponse.from(employee);
    }

    public List<DepartmentCount> activeByDepartment() {
        return repository.countByDepartment();
    }

    private Employee findOrThrow(Long id) {
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado: " + id));
    }

    private static WebApplicationException conflict(String message) {
        return new WebApplicationException(message, Response.Status.CONFLICT);
    }
}
