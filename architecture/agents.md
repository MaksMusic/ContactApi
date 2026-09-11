# AGENT.md

## Purpose

Follow these rules when creating or modifying Java code in this project.

The main goals are:
- keep code simple and readable;
- prefer modern Java features;
- avoid unnecessary abstractions;
- keep responsibilities small and explicit;
- follow existing Spring Boot project conventions.

---

## General Java Style

- Use Java 17+ language features where they improve readability.
- Prefer `var` for local variables when the type is obvious from the right-hand side.
- Use descriptive class, method, and variable names.
- Keep methods short and focused on one responsibility.
- Avoid deeply nested `if`/`else` blocks.
- Prefer early returns when they make the code easier to read.
- Avoid unnecessary comments. Code should explain itself through good naming.
- Do not introduce abstractions, interfaces, factories, or utility classes unless they solve an actual problem.
- Prefer immutable objects and immutable state where possible.
- Use `final` for fields that should not change.
- Avoid magic strings and magic numbers.
- Keep formatting consistent with the surrounding code.

Example:

```java
var requestHeader = request.getHeader(REQUEST_HEADER);

if (isNull(requestHeader)) {
    requestHeader = randomUUID().toString();
}
```

---

## Imports

- Prefer static imports when they make common operations easier to read.

Example:

```java
import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;
```

- Do not use static imports when they make the origin of a method unclear.
- Remove unused imports.

---

## Constants

- Constants must use `UPPER_SNAKE_CASE`.

Example:

```java
private static final String REQUEST_HEADER = "X-Request-Id";
```

- A small number of constants related only to one class may stay inside that class.
- If a class starts accumulating many constants, move them out.
- Prefer an `enum` when constants represent a fixed set of meaningful domain values.
- Prefer a dedicated constants class when the values are shared technical constants and an `enum` would not add semantic value.
- Do not create a global `Constants` class containing unrelated values.

Good:

```java
public enum RequestHeader {
    REQUEST_ID("X-Request-Id"),
    TRACE_ID("X-Trace-Id");

    private final String value;

    RequestHeader(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
```

or:

```java
public final class HttpHeadersConstants {

    public static final String REQUEST_ID = "X-Request-Id";
    public static final String TRACE_ID = "X-Trace-Id";

    private HttpHeadersConstants() {
    }
}
```

---

## DTOs

- Prefer Java `record` classes for DTOs.
- DTOs should normally contain data only.
- Avoid Lombok DTOs with mutable fields when a `record` is sufficient.
- Use validation annotations directly on record components when validation is required.
- Use regular classes only when mutability, inheritance, framework restrictions, or custom lifecycle behavior genuinely requires them.

Preferred:

```java
public record CreateUserRequest(
        @NotBlank String username,
        @Email String email
) {
}
```

Instead of:

```java
@Data
public class CreateUserRequest {
    private String username;
    private String email;
}
```

---

## Spring Components

- Use Spring stereotypes according to responsibility:
    - `@RestController` for REST controllers;
    - `@Service` for business logic;
    - `@Repository` for persistence-specific components;
    - `@Component` for generic Spring-managed components.
- Prefer constructor injection.
- Do not use field injection.
- Keep controllers thin.
- Controllers should handle HTTP concerns and delegate business logic to services.
- Business logic should not be placed in filters, controllers, repositories, or configuration classes.
- Keep configuration explicit and minimal.

---

## Request Filters

For classes extending `OncePerRequestFilter`:

- Keep filters focused on cross-cutting HTTP concerns.
- Do not put business logic inside filters.
- Read or generate request metadata in a simple and predictable way.
- Store repeated header names as constants.
- Add response headers only when needed.
- Logging should include useful request context such as request ID, HTTP method, and path.
- Always continue the filter chain unless the request is intentionally rejected.

Example:

```java
@Override
protected void doFilterInternal(
        HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
) throws ServletException, IOException {
    var requestId = request.getHeader(REQUEST_HEADER);

    if (isNull(requestId)) {
        requestId = randomUUID().toString();
    }

    response.addHeader(REQUEST_HEADER, requestId);

    log.info(
            "Получен запрос: {}. Тип запроса {}. Путь запроса {}.",
            requestId,
            request.getMethod(),
            request.getServletPath()
    );

    filterChain.doFilter(request, response);
}
```

Do not accidentally stop request processing by forgetting:

```java
filterChain.doFilter(request, response);
```

---

## Logging

- Use Lombok `@Slf4j` when Lombok is already used in the project.
- Use parameterized logging.

Preferred:

```java
log.info("Получен запрос: {}. Тип запроса {}.", requestId, request.getMethod());
```

Avoid:

```java
log.info("Получен запрос: " + requestId);
```

- Do not log passwords, access tokens, authorization headers, secrets, or sensitive personal information.
- Use log levels consistently:
    - `error` for failures requiring attention;
    - `warn` for abnormal but recoverable situations;
    - `info` for meaningful application events;
    - `debug` for diagnostic details;
    - `trace` only for very detailed diagnostics.

---

## Null Handling

- Avoid returning `null` when a meaningful empty value can be returned instead.
- Use `Optional` for return values only when absence is part of the API semantics.
- Do not use `Optional` for entity fields or DTO fields.
- Use `Objects.isNull` / `Objects.nonNull` when that style is already established in the project.
- Do not add null checks that cannot occur according to the contract.

---

## Methods and Responsibilities

- A method should do one thing.
- Extract private methods when they improve readability or remove duplicated logic.
- Do not split trivial logic into many tiny methods without a readability benefit.
- Prefer meaningful domain methods over generic names such as:
    - `process`;
    - `handle`;
    - `execute`;
    - `doStuff`.

Good:

```java
generateRequestId()
validateUserAccess()
saveOrder()
publishOrderCreatedEvent()
```

---

## Exceptions

- Do not catch `Exception` unless there is a strong reason.
- Catch the most specific exception possible.
- Do not silently swallow exceptions.
- Convert technical exceptions into domain/application exceptions only at appropriate boundaries.
- Preserve the original cause when wrapping exceptions.
- For REST APIs, handle application exceptions centrally using Spring exception handling.
- Prefer `@RestControllerAdvice` together with `@ExceptionHandler`.
- Do not duplicate `try/catch` blocks across controllers just to convert exceptions into HTTP responses.
- Controllers should throw or propagate meaningful application/domain exceptions and let the global exception handler map them to HTTP status codes and error responses.
- Keep exception-to-HTTP mapping in one dedicated exception handler class.
- Prefer a consistent error response DTO, ideally implemented as a Java `record`.
- Use `ResponseEntityExceptionHandler` only when overriding Spring MVC exception handling is actually needed.

Example application exception:

```java
throw new UserNotFoundException(userId, exception);
```

Example global Spring exception handler:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException exception) {
        var error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
```

Preferred error DTO:

```java
public record ApiError(
        int status,
        String message
) {
}
```

---

## Collections and Streams

- Prefer simple loops when they are more readable than streams.
- Use streams for clear transformation/filtering pipelines.
- Avoid long stream chains with complicated side effects.
- Do not use `peek()` for business logic.
- Return empty collections instead of `null`.

---

## Entity and Persistence Rules

- Do not expose JPA entities directly from REST controllers.
- Map entities to DTOs.
- Keep persistence-specific logic inside repositories or persistence adapters.
- Avoid unnecessary bidirectional JPA relationships.
- Be careful with lazy-loaded relationships.
- Use transactions at the service layer when they represent a business operation.

---

## Validation

- Validate external input at the application boundary.
- Prefer Jakarta Validation annotations for DTO validation.
- Do not duplicate the same validation rules manually in several layers.
- Business rules that depend on application state belong in the service/domain layer.

---

## Naming

Use names that describe intent.

Preferred:

```java
requestId
userRepository
findActiveUsers()
createOrder()
```

Avoid abbreviations unless they are standard and obvious.

Avoid names such as:

```java
data
obj
tmp
value1
manager
helper
util
```

when a more specific name is possible.

---

## Clean Code Priorities

When changing existing code, prefer this order:

1. Correctness.
2. Readability.
3. Simplicity.
4. Consistency with the existing project.
5. Testability.
6. Performance, unless performance is already a known problem.

Do not make code more abstract or complicated only because a design pattern could be applied.

---

## Code Generation Rules

When generating new code:

- Match the package structure already used by the project.
- Match existing naming and formatting conventions.
- Reuse existing utilities and abstractions when they are appropriate.
- Do not duplicate existing functionality.
- Prefer records for DTOs.
- Prefer enums or dedicated constants classes when many related constants exist.
- Keep Spring components focused on a single responsibility.
- Add validation where external input enters the application.
- Add tests for non-trivial business logic.
- Ensure filters call `filterChain.doFilter(...)` unless intentionally terminating the request.
- Do not add dependencies unless they are necessary.
- Do not refactor unrelated code unless required for the requested change.

---

## Testing

- Unit-test business logic.
- Use integration tests where behavior depends on Spring, persistence, Kafka, HTTP, or other infrastructure.
- Tests should describe behavior, not implementation details.
- Use descriptive test names.

Example:

```java
shouldGenerateRequestIdWhenHeaderIsMissing()
shouldReuseRequestIdFromRequestHeader()
```

- Prefer Arrange / Act / Assert structure.
- Avoid excessive mocking.
- Mock external boundaries, not simple value objects.

---

## Final Review Checklist

Before finishing a change, verify that:

- the code compiles;
- imports are clean;
- naming is clear;
- no unnecessary abstraction was introduced;
- DTOs use `record` where appropriate;
- repeated or numerous constants are represented by an `enum` or dedicated constants class where appropriate;
- controllers remain thin;
- filters continue the filter chain;
- sensitive information is not logged;
- external input is validated;
- exceptions are handled intentionally;
- tests cover important behavior;
- formatting matches the existing project.
