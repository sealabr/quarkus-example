package com.example.quarkus.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    public Optional<Employee> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public List<Employee> search(String department, Boolean active) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("1 = 1");

        if (department != null && !department.isBlank()) {
            query.append(" and department = :department");
            params.put("department", department);
        }
        if (active != null) {
            query.append(" and active = :active");
            params.put("active", active);
        }

        return list(query.toString(), Sort.by("name"), params);
    }

    public List<DepartmentCount> countByDepartment() {
        return getEntityManager()
                .createQuery("""
                        select e.department, count(e)
                        from Employee e
                        where e.active = true
                        group by e.department
                        order by e.department
                        """, Object[].class)
                .getResultList()
                .stream()
                .map(row -> new DepartmentCount((String) row[0], (Long) row[1]))
                .toList();
    }
}
