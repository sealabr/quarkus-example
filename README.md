# Quarkus & Spring — API de RH (funcionários)

Exemplo **realista e enxuto**: cadastro de funcionários (RH), com persistência, validação e regras de negócio — lado a lado em Quarkus e Spring Boot.

| Módulo | Framework | Versão | Java | Porta |
|--------|-----------|--------|------|-------|
| `quarkus-app` | Quarkus + Panache | 3.38.1 | 25 | 8080 |
| `spring-app` | Spring Boot + Data JPA | 4.1.0 | 26 | 8081 |

> Quarkus 3.38 oficialmente cobre Java **17–25**. Spring Boot 4.1 cobre até **26**.

## O que o app faz

Mini API de RH:

| Método | Path | Ação |
|--------|------|------|
| `GET` | `/api/employees` | Lista (`?department=TI&active=true`) |
| `GET` | `/api/employees/{id}` | Busca por id |
| `POST` | `/api/employees` | Contrata (e-mail único, data ≤ hoje) |
| `PUT` | `/api/employees/{id}` | Atualiza (só se ativo) |
| `POST` | `/api/employees/{id}/deactivate` | Desliga (soft delete) |
| `GET` | `/api/employees/stats/by-department` | Ativos por departamento |

**Regras de negócio (mundo real, versão simples):**
- e-mail único
- data de admissão não pode ser futura (`@PastOrPresent`)
- inativo não é atualizado
- desligamento é desativação, não delete físico

## Camadas (como em produção)

```
Resource/Controller  →  HTTP / JSON / status
Service              →  regras de negócio + @Transactional
Repository           →  acesso a dados (Panache | Spring Data)
Entity               →  tabela employee
DTO (Request/Response) → contrato da API (não expõe a entidade crua)
```

## Pré-requisitos

- Maven 3.9+
- JDK **25+** (`quarkus-app`) / JDK **26** (`spring-app`)
- Sem Docker: ambos usam **H2 em memória**

## Como rodar

### Quarkus

```bash
cd quarkus-app
mvn quarkus:dev
```

```bash
# listar
curl http://localhost:8080/api/employees

# filtrar TI ativos
curl "http://localhost:8080/api/employees?department=TI&active=true"

# contratar
curl -X POST http://localhost:8080/api/employees ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Diego Alves\",\"email\":\"diego@empresa.com\",\"department\":\"Financeiro\",\"hireDate\":\"2024-05-20\"}"

# desligar
curl -X POST http://localhost:8080/api/employees/1/deactivate

# relatório
curl http://localhost:8080/api/employees/stats/by-department
```

### Spring Boot

```bash
cd spring-app
mvn spring-boot:run
```

Mesmos paths na porta **8081**.

## Estrutura

```
quarkus-app/.../hr/     Employee, EmployeeResource, EmployeeService, EmployeeRepository, DTOs
spring-app/.../hr/      Employee, EmployeeController, EmployeeService, EmployeeRepository, DTOs
docs/comparacao-quarkus-spring.md
```

## Documentação

- [Comparação Quarkus × Spring](docs/comparacao-quarkus-spring.md)

## Próximos passos naturais

- PostgreSQL + Dev Services / Testcontainers
- Paginação e ordenação
- Segurança (OIDC / Spring Security)
- Histórico de cargos / folha
