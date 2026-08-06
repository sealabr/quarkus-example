package com.example.spring;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.spring.hr.Employee;
import com.example.spring.hr.EmployeeRepository;

@SpringBootApplication
public class SpringApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

    @Bean
    CommandLineRunner seed(EmployeeRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.saveAll(List.of(
                    new Employee("Ana Costa", "ana.costa@empresa.com", "TI", LocalDate.of(2022, 3, 15), true),
                    new Employee("Bruno Lima", "bruno.lima@empresa.com", "RH", LocalDate.of(2021, 8, 1), true),
                    new Employee("Carla Souza", "carla.souza@empresa.com", "TI", LocalDate.of(2020, 1, 10), false)
            ));
        };
    }
}
