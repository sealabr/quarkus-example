package com.example.quarkus.hr;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidade de funcionário (persistida com Hibernate + Panache).
 * PanacheEntity já traz id Long gerado e helpers de query.
 */
@Entity
@Table(name = "employee")
public class Employee extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String department;

    @Column(name = "hire_date", nullable = false)
    public LocalDate hireDate;

    @Column(nullable = false)
    public boolean active = true;
}
