You are working in this repository.

Before making any changes:

1. Read and follow the repository's `AGENTS.md`.
2. Inspect the relevant existing code, tests, configuration, and nearby package structure.
3. Follow the existing Java, Spring Boot, build-tool, formatting, testing, and architectural conventions.
4. Reuse existing utilities, mappers, exception handling, DTO conventions, constants, and abstractions where appropriate.
5. Keep the scope of the change narrow.
6. Do not refactor unrelated code.
7. Do not add dependencies, abstractions, interfaces, or design patterns unless they solve a concrete problem.
8. Do not remove validation, security checks, tests, or error handling merely to make the implementation easier.
9. Prefer the simplest correct implementation that is easy to read and test.

When working with Java/Spring code:

* Prefer Java records for DTOs when appropriate.
* Use MapStruct for DTO/entity mapping where appropriate.
* Use centralized Spring exception handling with `@RestControllerAdvice` / `@ExceptionHandler`.
* Prefer Spring `ProblemDetail` for REST error responses when supported by the project.
* Prefer constructor injection.
* Keep controllers thin.
* Keep business logic in the service/domain layer.
* Keep persistence concerns in repositories/persistence adapters.
* Do not expose JPA entities directly through REST APIs.
* Use Jakarta Validation for external input.
* Keep transaction boundaries intentional and primarily in the service/application layer.
* Do not log secrets, tokens, credentials, or sensitive request data.
* Avoid manual repetitive mapping when MapStruct can handle it.
* Keep constants close to their owner; use enums only for meaningful closed sets and dedicated constants classes for groups of shared technical constants.

Before finishing:

1. Make sure the code compiles.
2. Run the relevant tests.
3. Run existing project quality checks when available.
4. Check for unused imports, accidental API changes, missing validation, unmapped DTO/entity fields, sensitive logging, and obvious persistence problems such as N+1 queries.
5. Add or update tests for meaningful behavior changes.
6. Report any assumption or verification step that could not be completed.

Do not change anything outside the requested scope unless it is required for correctness.

## Developer additions

ВАШУ ЗАДАЧУ ОПИСЫВАТЬ НИЖЕ!!!

Пример:

```text
Task:
Add an endpoint for retrieving a user by external ID. (Описание вашей задачи)

Additional requirements: (Дополнительные условия если надо)
- Return HTTP 404 when the user does not exist.
- Use the existing UserMapper.
- Do not change the database schema.
- Add controller and service tests.
```
