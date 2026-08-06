# Comparação: Quarkus × Spring Boot

Guia rápido para quem já conhece um e está aprendendo o outro.

## Versões neste repo

| | Quarkus | Spring Boot |
|--|---------|-------------|
| Versão | 3.38.1 | 4.1.0 |
| Java suportado | 17–25 | 17–26 |
| Java neste módulo | 25 | 26 |
| DI | CDI (ArC) | Spring IoC |
| REST | Jakarta REST (Quarkus REST) | Spring MVC |
| Config | MicroProfile Config | Spring Environment |
| Dev mode | `mvn quarkus:dev` | `mvn spring-boot:run` / DevTools |

## REST — lado a lado

**Quarkus**

```java
@Path("/api/greetings")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @GET
    @Path("/{name}")
    public GreetingResponse greet(@PathParam("name") String name) {
        return greetingService.greet(name);
    }
}
```

**Spring**

```java
@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/{name}")
    public GreetingResponse greet(@PathVariable String name) {
        return greetingService.greet(name);
    }
}
```

## Injeção de dependência

| | Quarkus (CDI) | Spring |
|--|---------------|--------|
| Bean de app | `@ApplicationScoped` | `@Component` / `@Service` |
| Injeção | `@Inject` (campo ou construtor) | construtor (preferido) ou `@Autowired` |
| Escopo request | `@RequestScoped` | `@RequestScope` |
| Produtor | `@Produces` | `@Bean` |

## Configuração

**Quarkus** — propriedade avulsa:

```java
@ConfigProperty(name = "app.greeting.prefix", defaultValue = "Hello")
String prefix;
```

**Spring** — objeto tipado:

```java
@ConfigurationProperties(prefix = "app.greeting")
public record GreetingProperties(String prefix) {}
```

Em Quarkus também existe `@ConfigMapping` para o estilo tipado (semelhante ao Spring).

## Ciclo de vida e build

| | Quarkus | Spring Boot |
|--|---------|-------------|
| Build time | Processamento agressivo em build (menos reflexão em runtime) | Autoconfig em runtime (AOT opcional) |
| Hot reload | Nativo no Dev Mode | Spring DevTools (opcional) |
| Native | Prioridade do ecossistema (Mandrel/GraalVM) | Suportado via GraalVM + AOT |
| Ecossistema | Extensões Quarkus + Jakarta / MicroProfile | Starters Spring + vasto ecossistema |

## Quando escolher qual

- **Quarkus:** startup baixo, native image, APIs Jakarta/MicroProfile, Dev Mode forte.
- **Spring Boot:** ecossistema maduro, documentação vasta, times já Spring, suporte Java 26 neste momento.

## Comandos úteis

```bash
# Quarkus
mvn -f quarkus-app quarkus:dev
mvn -f quarkus-app package

# Spring
mvn -f spring-app spring-boot:run
mvn -f spring-app package
```
