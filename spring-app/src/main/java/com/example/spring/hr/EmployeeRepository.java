package com.example.spring.hr;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentIgnoreCaseAndActive(String department, boolean active);

    List<Employee> findByDepartmentIgnoreCase(String department);

    List<Employee> findByActive(boolean active);

    List<Employee> findAllByOrderByNameAsc();

    @Query("""
            select new com.example.spring.hr.DepartmentCount(e.department, count(e))
            from Employee e
            where e.active = true
            group by e.department
            order by e.department
            """)
    List<DepartmentCount> countActiveByDepartment();
}
