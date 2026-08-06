# Quarkus & Spring — exemplos de uso

Repositório de referência para comparar e praticar **Quarkus** e **Spring Boot** com Java moderno.

| Módulo | Framework | Versão | Java alvo | Porta |
|--------|-----------|--------|-----------|-------|
| `quarkus-app` | Quarkus | 3.38.1 | **25** (máx. oficial) | 8080 |
| `spring-app` | Spring Boot | 4.1.0 | **26** | 8081 |

> **Java 26:** Spring Boot 4.1 suporta até Java 26. Quarkus 3.38 oficialmente cobre **17–25** (ainda sem suporte a 26). Por isso o módulo Quarkus usa 25.

## Pré-requisitos

- Maven 3.9+
- JDK **25+** para `quarkus-app`
- JDK **26** para `spring-app` (ou ajuste `java.version` no `pom.xml` se usar JDK 25)

Neste ambiente, o Maven está apontando para JDK 17 — instale JDK 25/26 e configure `JAVA_HOME` antes de compilar.

## Como rodar

### Quarkus (dev mode — hot reload)

```bash
cd quarkus-app
mvn quarkus:dev
```

```bash
curl http://localhost:8080/api/greetings/Fabio
```

### Spring Boot

```bash
cd spring-app
mvn spring-boot:run
```

```bash
curl http://localhost:8081/api/greetings/Fabio
```

Resposta esperada (conceito):

```json
{ "message": "Olá, Fabio!", "framework": "quarkus" }
```

## O que cada exemplo cobre

| Conceito | Quarkus | Spring |
|----------|---------|--------|
| REST JSON | `@Path` + Jakarta REST | `@RestController` + Spring MVC |
| Injeção de dependência | CDI `@ApplicationScoped` + `@Inject` | `@Service` + construtor |
| Configuração | `@ConfigProperty` / `application.properties` | `@ConfigurationProperties` |
| Validação | Hibernate Validator | Bean Validation |
| JSON | `quarkus-rest-jackson` | Jackson (starter-web) |
| Record como DTO | `GreetingResponse` | `GreetingResponse` |

## Estrutura

```
framework-examples/
├── pom.xml                 # agregador Maven
├── docs/
│   └── comparacao-quarkus-spring.md
├── quarkus-app/
│   └── src/main/java/com/example/quarkus/
└── spring-app/
    └── src/main/java/com/example/spring/
```

## Documentação

- [Comparação Quarkus × Spring](docs/comparacao-quarkus-spring.md)

## Próximos temas (sugeridos)

- Persistência: Panache / Hibernate vs Spring Data JPA
- REST Client: MicroProfile Rest Client vs `RestClient` / WebClient
- Segurança: Quarkus OIDC vs Spring Security
- Native image: Mandrel/GraalVM vs Spring AOT + GraalVM
