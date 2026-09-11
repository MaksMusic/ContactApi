# AGENTS.md

## Purpose

These instructions apply to AI coding agents and developers modifying this repository.

Primary goals:

1. Correctness.
2. Readability.
3. Simplicity.
4. Consistency with the existing project.
5. Testability.
6. Security.
7. Performance when there is a demonstrated need.

Prefer the smallest clean change that solves the requested problem. Do not introduce abstractions, dependencies, patterns, or refactors without a concrete reason.

---

## Agent Workflow

Before changing code:

- Read the relevant existing classes, tests, configuration, and nearby package structure.
- Follow the project's configured Java and Spring Boot versions. Do not upgrade them unless explicitly requested.
- Reuse existing project conventions before introducing new ones.
- Check whether similar functionality already exists before adding another implementation.
- Keep the scope of the change narrow. Do not refactor unrelated code.
- Do not modify generated files directly.
- Do not remove tests, validation, security checks, or error handling merely to make a build pass.

After changing code:

- Compile the affected module.
- Run the smallest relevant tests first.
- Run the full module/project test suite when practical.
- Run existing formatting/static-analysis tasks if the project already defines them.
- Report anything that could not be verified.

---

## Build and Verification

Use the repository's build wrapper when available.

For Gradle projects:

```bash
./gradlew test
./gradlew check
```

For Maven projects:

```bash
./mvnw test
./mvnw verify
```

On Windows, use the corresponding wrapper scripts when required:

```text
gradlew.bat
mvnw.cmd
```

Rules:

- Prefer the wrapper checked into the repository over a globally installed build tool.
- Do not add or upgrade Maven/Gradle plugins only to satisfy style preferences.
- If `check`, `verify`, Spotless, Checkstyle, PMD, Sonar, or another quality task already exists, use the existing configuration.
- Do not invent a new formatting standard if the repository already has one.

---

## General Java Style

- Prefer modern Java features supported by the project's configured Java version.
- Prefer `var` for local variables only when the inferred type is obvious.
- Use descriptive names.
- Keep methods focused on one responsibility.
- Prefer early returns over deep nesting when they improve readability.
- Use braces for control-flow statements.
- Avoid wildcard imports.
- Remove unused imports.
- Prefer immutable state.
- Use `final` for fields that should not change.
- Avoid magic strings and magic numbers.
- Avoid unnecessary comments. Explain *why* when the reason is not obvious; do not narrate obvious code.
- Do not create interfaces with a single implementation only for the sake of having an interface.
- Do not create `Util`, `Helper`, `Manager`, or generic abstraction classes when a more specific responsibility can be named.
- Prefer composition over inheritance unless inheritance clearly models the domain/framework contract.
- Do not optimize code before there is evidence that optimization is needed.

Example:

```java
var requestId = request.getHeader(REQUEST_ID_HEADER);

if (isNull(requestId)) {
    requestId = randomUUID().toString();
}
```

---

## Imports

Static imports are allowed when they improve readability.

Good:

```java
import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;
```

Rules:

- Do not use static imports when the origin of the method becomes unclear.
- Never use wildcard imports.
- Keep imports consistent with the repository formatter/style.

---

## Naming

Names must communicate intent.

Preferred:

```java
requestId
userRepository
findActiveUsers()
createOrder()
publishOrderCreatedEvent()
```

Avoid vague names when a precise name is possible:

```java
data
obj
tmp
value1
manager
helper
util
process
handle
doStuff
```

`process` or `handle` are acceptable only when they genuinely describe the framework/domain responsibility.

---

## Constants and Enums

Constants use `UPPER_SNAKE_CASE`.

Example:

```java
private static final String REQUEST_ID_HEADER = "X-Request-Id";
```

Rules:

- Keep a small number of class-specific constants inside the class that owns them.
- When many related technical constants are shared, move them to a dedicated constants class.
- Use an `enum` when values represent a closed, meaningful domain set.
- Do not use an `enum` merely as a container for unrelated string constants.
- Do not create one global `Constants` class containing unrelated values.
- Do not duplicate the same literal across multiple classes.

Domain example:

```java
public enum OrderStatus {
    CREATED,
    PAID,
    CANCELLED
}
```

Technical constants example:

```java
public final class HttpHeaderConstants {

    public static final String REQUEST_ID = "X-Request-Id";
    public static final String TRACE_ID = "X-Trace-Id";

    private HttpHeaderConstants() {
    }
}
```

---

## DTOs

Prefer Java `record` classes for request/response DTOs when the DTO is a simple data carrier.

Preferred:

```java
public record CreateUserRequest(
        @NotBlank String username,
        @Email String email
) {
}
```

Rules:

- DTOs should normally contain data, validation metadata, and only trivial derived behavior.
- Prefer immutable DTOs.
- Avoid Lombok `@Data` DTOs when a record is sufficient.
- Put Jakarta Validation annotations directly on record components.
- Use separate request and response DTOs when their responsibilities differ.
- Do not reuse JPA entities as REST request/response models.
- Use a regular class instead of a record when mutability, inheritance, proxying, or framework requirements genuinely require it.

---

## MapStruct: DTO <-> Entity Mapping

Use MapStruct for non-trivial mapping between DTOs and entities.

Prefer one shared configuration:

```java
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MapperConfiguration {
}
```

Mapper example:

```java
@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

    UserResponse toResponse(User entity);

    User toEntity(CreateUserRequest request);
}
```

Rules:

- Keep mapping logic in mapper interfaces/classes, not controllers.
- Avoid repetitive field-by-field mapping in services.
- Map in both directions only when both directions are actually required.
- Use `unmappedTargetPolicy = ReportingPolicy.ERROR` so new target fields do not get silently forgotten.
- Use constructor injection for mapper dependencies.
- Use `uses = ...` for reusable nested mappers/converters.
- Use explicit `@Mapping` only when field names/types/semantics differ.
- Explicitly ignore fields that must not be supplied by the client, such as generated IDs or server-managed audit fields.
- Do not put business rules in mappers.
- Do not call repositories or remote services from mappers.
- Prefer compile-time mapping over reflection-based mapping libraries.

Example with different field names:

```java
@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

    @Mapping(source = "createdAt", target = "createdDate")
    UserResponse toResponse(User entity);
}
```

### Updating Existing Entities

For updates, map into the already loaded entity:

```java
void update(
        UpdateUserRequest request,
        @MappingTarget User entity
);
```

For PATCH semantics, ignoring `null` values may be appropriate:

```java
@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
void patch(
        UpdateUserRequest request,
        @MappingTarget User entity
);
```

Do **not** automatically ignore `null` for PUT semantics. For PUT, nullability should follow the API contract and validation rules.

---

## Spring Components and Dependency Injection

Use stereotypes according to responsibility:

- `@RestController` — HTTP/API boundary.
- `@Service` — application/business logic.
- `@Repository` — persistence boundary when a repository implementation is required.
- `@Component` — generic Spring-managed infrastructure component.
- `@Configuration` — bean/configuration definitions.

Rules:

- Prefer constructor injection.
- Do not use field injection.
- When a Spring bean has one constructor, do not add unnecessary `@Autowired`.
- Keep controllers thin.
- Controllers should validate/translate HTTP input and delegate use cases to services.
- Do not put business logic in controllers, filters, repositories, mappers, or configuration classes.
- Do not add an interface for every service unless there is a real boundary, multiple implementations, or another concrete reason.

Preferred:

```java
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
}
```

---

## Configuration

- Prefer type-safe `@ConfigurationProperties` for groups of related configuration values.
- Prefer constructor-bound/immutable configuration objects where supported.
- Use `@Value` only for isolated simple values when a configuration properties type would be excessive.
- Never hard-code environment-specific URLs, credentials, tokens, passwords, or ports in business code.
- Do not commit secrets.
- Keep environment differences in external configuration.

---

## REST API Rules

- Do not expose JPA entities directly.
- Use DTOs at API boundaries.
- Validate external input.
- Use appropriate HTTP methods and status codes.
- Keep resource naming consistent with the existing API.
- Do not change public API contracts without explicitly considering backward compatibility.
- Do not return stack traces or internal exception details to clients.
- Use pagination for endpoints that can return unbounded collections.
- Do not fetch an unbounded table and filter/page it in memory.
- Keep controller methods small and declarative.

Example:

```java
@PostMapping
public ResponseEntity<UserResponse> create(
        @Valid @RequestBody CreateUserRequest request
) {
    var response = userService.create(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
}
```

---

## Validation

Use Jakarta Bean Validation at input boundaries.

Example:

```java
public record CreateUserRequest(
        @NotBlank
        @Size(max = 100)
        String username,

        @NotBlank
        @Email
        String email
) {
}
```

Rules:

- Use `@Valid` / `@Validated` where required by Spring MVC.
- Validate shape/format at the boundary.
- Put rules that depend on database/application state in the service/domain layer.
- Do not duplicate the same validation logic in controller and service.
- Return a consistent error representation for validation failures.
- Do not trust headers, path variables, query parameters, or request bodies merely because they came through a typed method signature.

---

## Exception Handling

REST exceptions should be handled centrally.

Use:

- `@RestControllerAdvice`
- `@ExceptionHandler`
- Spring `ProblemDetail` / RFC 9457 for REST error responses when the project uses Spring 6+.

Preferred:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException exception) {
        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("User not found");
        problem.setProperty("code", "USER_NOT_FOUND");

        return problem;
    }
}
```

Rules:

- Do not duplicate exception-to-response `try/catch` blocks in controllers.
- Throw meaningful application/domain exceptions from the appropriate layer.
- Map exceptions to HTTP responses in one centralized advice.
- Catch the most specific exception possible.
- Do not catch `Exception` unless there is a strong boundary-level reason.
- Do not silently swallow exceptions.
- Preserve the original cause when wrapping technical exceptions.
- Do not expose SQL errors, framework internals, stack traces, credentials, or sensitive information to API clients.
- Prefer stable machine-readable error codes for client logic.
- Use `ResponseEntityExceptionHandler` only when its built-in Spring MVC exception handling/customization is useful.
- Handle validation failures consistently with the same error format.

---

## Persistence and JPA

- Keep persistence concerns in repositories/persistence adapters.
- Do not expose entities from REST controllers.
- Prefer regular classes for JPA entities; DTO records are not entity replacements.
- Avoid Lombok `@Data` on JPA entities.
- Be deliberate with `equals()` / `hashCode()` on entities, especially when IDs are generated and relationships are lazy.
- Prefer lazy relationships unless eager loading is intentionally required.
- Avoid unnecessary bidirectional relationships.
- Do not serialize lazy entity graphs through API responses.
- Watch for N+1 queries. Use fetch joins, entity graphs, projections, or query redesign when needed.
- Do not solve N+1 by making every relationship eager.
- Use Spring Data projections when only a small subset of columns is needed and it materially improves the query.
- Do not concatenate user input into SQL/JPQL.

---

## Transactions

Place transaction boundaries around application/service operations.

Rules:

- Prefer declarative `@Transactional`.
- Put transaction boundaries in the service/application layer, not controllers.
- Use `@Transactional(readOnly = true)` for read-only use cases when appropriate.
- Keep transactions as short as practical.
- Avoid slow remote HTTP/Kafka/external calls inside a database transaction unless atomicity requirements explicitly justify it.
- Be aware that Spring proxy-based `@Transactional` does not behave as expected on self-invocation.
- Do not add `REQUIRES_NEW`, custom isolation, or custom rollback rules without a specific requirement.

---

## Request Filters

For `OncePerRequestFilter`:

- Keep filters focused on cross-cutting HTTP concerns.
- Do not put business logic in filters.
- Always continue the chain unless intentionally rejecting the request.
- Declare `ServletException` and `IOException` as required by the filter contract.
- For a single-valued response header, prefer `setHeader` rather than `addHeader`.
- Treat client-supplied correlation/request IDs as untrusted input; validate format/length if they are accepted.
- If the project already uses Micrometer Tracing, prefer its trace/span correlation and avoid inventing a parallel mechanism unless `X-Request-Id` is part of an external contract.

Preferred request ID filter pattern:

```java
@Slf4j
@Component
public class RequestIdFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String REQUEST_ID_MDC_KEY = "requestId";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var requestId = request.getHeader(REQUEST_ID_HEADER);

        if (isNull(requestId) || requestId.isBlank()) {
            requestId = randomUUID().toString();
        }

        response.setHeader(REQUEST_ID_HEADER, requestId);
        MDC.put(REQUEST_ID_MDC_KEY, requestId);

        try {
            log.debug(
                    "Request received. id={}, method={}, path={}",
                    requestId,
                    request.getMethod(),
                    request.getRequestURI()
            );

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID_MDC_KEY);
        }
    }
}
```

Why:

- `filterChain.doFilter(...)` prevents accidentally terminating every request.
- `setHeader(...)` avoids duplicate values for a single-valued request ID.
- MDC makes the request ID available to all logs on the request thread.
- `finally` prevents context leakage when servlet threads are reused.
- Request-per-request logging should generally be `debug` unless the project's logging policy explicitly requires `info`.

---

## Logging

Use parameterized logging.

Preferred:

```java
log.info("User created. userId={}", userId);
```

Avoid:

```java
log.info("User created. userId=" + userId);
```

Rules:

- Never log passwords, access tokens, refresh tokens, authorization headers, secrets, or private keys.
- Avoid logging entire request/response bodies by default.
- Avoid logging sensitive personal data.
- Log identifiers and useful context rather than dumping full objects.
- Do not log the same exception repeatedly at multiple layers.
- Log an exception where it is actually handled or where additional context is added.
- Use levels consistently:
  - `error` — operation failed and requires attention.
  - `warn` — abnormal/recoverable situation.
  - `info` — meaningful business/application lifecycle event.
  - `debug` — diagnostic detail.
  - `trace` — very detailed diagnostics.

---

## Null Handling and Optional

- Prefer non-null contracts.
- Return empty collections rather than `null`.
- Use `Optional<T>` for return values when absence is a normal outcome.
- Do not use `Optional` for DTO/entity fields.
- Do not use `Optional` as a method parameter unless an existing API requires it.
- Do not add defensive null checks for states that are impossible by contract.
- Follow the project's existing nullability annotations (`@NonNull`, JSpecify, etc.).

---

## Collections and Streams

- Prefer a loop when it is clearer than a stream.
- Use streams for straightforward transformation/filtering pipelines.
- Avoid long, nested stream expressions.
- Avoid side effects inside streams.
- Do not use `peek()` for business logic.
- Prefer immutable/unmodifiable results where mutation is not required.
- Do not repeatedly traverse large collections when one pass is sufficient.

---

## Date, Time, and Money

- Use `java.time` APIs, not legacy `Date`/`Calendar`, unless integrating with a legacy API.
- Be explicit about time zones for cross-system timestamps.
- Prefer `Instant` for machine timestamps and an appropriate local type for domain-local date/time.
- Do not use `double`/`float` for monetary values. Prefer `BigDecimal` or a dedicated money type used by the project.
- Avoid calling `now()` deep inside business logic when deterministic testing requires an injectable `Clock`.

---

## Security

- Treat all external input as untrusted.
- Never commit credentials or secrets.
- Never log credentials/tokens.
- Use parameterized database queries.
- Do not bypass authorization checks for convenience.
- Do not bind request DTOs directly onto entities in a way that allows clients to set server-managed fields.
- Do not expose internal IDs/secrets unless the API contract explicitly requires them.
- Validate uploaded file type/size and path handling when file upload exists.
- Keep dependency changes minimal and intentional.

---

## Testing

Add or update tests for meaningful behavior changes.

Use the smallest appropriate test type:

- plain unit test for isolated business logic;
- `@WebMvcTest` for MVC/controller behavior;
- `@DataJpaTest` for repository behavior;
- `@SpringBootTest` when full application wiring is actually required;
- Testcontainers when behavior depends on the real database/broker semantics.

Rules:

- Prefer behavior-focused tests over implementation-detail tests.
- Use descriptive names.

Examples:

```java
shouldGenerateRequestIdWhenHeaderIsMissing()
shouldReuseRequestIdFromRequestHeader()
shouldReturnNotFoundWhenUserDoesNotExist()
```

- Follow the test framework/assertion library already used by the project.
- Avoid excessive mocking.
- Mock external boundaries, not simple value objects.
- Do not mock the class under test.
- For database-specific behavior, prefer testing against the same database family used in production.
- Test custom MapStruct mappings when they contain conversions/expressions with meaningful behavior; do not write redundant tests for trivial generated assignments.

---

## Comments and Documentation

- Prefer clear code over explanatory comments.
- Add comments for non-obvious constraints, workarounds, compatibility reasons, or domain rules.
- Delete stale comments when code changes.
- Public API documentation should describe contract and semantics, not implementation details.
- Do not add large Javadocs to obvious getters/setters/trivial methods.

---

## Dependency Rules

- Do not add a new dependency if the JDK, Spring, or an existing dependency already solves the problem cleanly.
- Do not upgrade dependency versions unless requested or required by the task.
- Prefer established project libraries over introducing competing libraries for the same concern.
- Check licensing/security/project policy before introducing a new third-party dependency when relevant.

---

## Generated and Boilerplate Code

- Do not manually edit generated MapStruct implementations.
- Do not commit generated build output unless the repository already does so intentionally.
- Use Lombok only where the project already uses it and it improves readability.
- Avoid Lombok `@Data` on JPA entities.
- Prefer explicit constructors for Spring services when clarity matters; Lombok constructor generation is acceptable if it is already a project convention.

---

## Final Review Checklist

Before finishing a change, verify:

- code compiles;
- relevant tests pass;
- project formatting/static checks pass when configured;
- no unused/wildcard imports were introduced;
- naming is clear;
- no unrelated refactor was introduced;
- no unnecessary interface/abstraction/dependency was added;
- DTOs use records where appropriate;
- entities are not exposed through the REST API;
- MapStruct is used for DTO/entity mapping where appropriate;
- MapStruct does not silently ignore unmapped target fields;
- constants are located appropriately;
- enums are used only for meaningful closed sets;
- controllers remain thin;
- constructor injection is used;
- external input is validated;
- REST exceptions are handled centrally;
- error responses do not reveal internals;
- transaction boundaries are intentional;
- filters call `filterChain.doFilter(...)`;
- single-valued headers use `setHeader(...)`;
- logging does not expose sensitive information;
- JPA changes do not obviously introduce N+1 queries;
- tests cover the changed behavior;
- public API compatibility has been considered;
- formatting matches the existing repository.
