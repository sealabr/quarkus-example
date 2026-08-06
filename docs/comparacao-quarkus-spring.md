# Comparação: Quarkus × Spring Boot (API de RH)

Mesmo domínio nos dois módulos: **funcionários**.

## Mapa de conceitos

| Conceito | Quarkus | Spring |
|----------|---------|--------|
| REST | `@Path` / `@GET` (Jakarta REST) | `@RestController` / `@GetMapping` |
| DI | `@ApplicationScoped` + `@Inject` | `@Service` + construtor |
| Entidade | `@Entity` + `PanacheEntity` | `@Entity` + getters/setters |
| Repositório | `PanacheRepository` | `JpaRepository` |
| Transação | `@Transactional` (Jakarta) | `@Transactional` (Spring) |
| Validação | `@Valid` + Bean Validation | igual |
| Persistência | Hibernate ORM | Hibernate (Spring Data JPA) |
| DB local | H2 (`quarkus-jdbc-h2`) | H2 |

## REST — lado a lado

**Quarkus** (`EmployeeResource`)

```java
@Path("/api/employees")
public class EmployeeResource {

    @Inject
    EmployeeService service;

    @GET
    public List<EmployeeResponse> list(
            @QueryParam("department") String department,
            @QueryParam("active") Boolean active) {
        return service.list(department, active);
    }

    @POST
    public Response hire(@Valid EmployeeRequest request) { ... }
}
```

**Spring** (`EmployeeController`)

```java
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public List<EmployeeResponse> list(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean active) {
        return service.list(department, active);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> hire(@Valid @RequestBody EmployeeRequest request) { ... }
}
```

## Persistência

**Quarkus (Panache)** — campos públicos, queries curtas:

```java
public class Employee extends PanacheEntity {
    public String name;
    public String email;
    // ...
}

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
    public Optional<Employee> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
```

**Spring Data** — interface + nomes de método:

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
```

## Regras de negócio (iguais nos dois)

1. Contratar com e-mail único  
2. `hireDate` não futura  
3. Não atualizar inativo  
4. Desligamento = `active = false`  

Isso é o que falta no “Hello World”: **camadas + estado persistido + conflitos HTTP (409/404)**.

## Quando escolher qual

- **Quarkus:** Dev Mode, Panache enxuto, native image, APIs Jakarta.
- **Spring:** ecossistema Data/Security maduro, times já Spring, Java 26 hoje.

## Comandos

```bash
mvn -f quarkus-app quarkus:dev
mvn -f spring-app spring-boot:run
```
