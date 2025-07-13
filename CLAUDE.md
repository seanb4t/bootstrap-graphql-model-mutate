# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## JSpecify Nullability Annotations

This project uses JSpecify 1.0.0 for null-safety annotations. These annotations improve interoperability between Java and Kotlin code.

### Key Guidelines
- The base package `dev.fzymgc.bootstrap.graphqlmodelmutate` is marked with `@NullMarked`, making all types non-null by default
- Use `@Nullable` annotation for parameters, return types, or fields that can be null
- Kotlin compiler is configured with strict nullability checking via `-Xjsr305=strict` and `-Xnullability-annotations=@org.jspecify.annotations:strict`
- Both Kotlin and Java code should use these annotations for public APIs

### Example Usage
```java
// Java example
public String processValue(@Nullable String input) {
    return input != null ? input : "default";
}
```

```kotlin
// Kotlin example - no @Nullable needed, use ? for nullable types
fun findUser(id: String): User? {
    return repository.findById(id)
}
```

### Important Notes
- In Kotlin code, do NOT use @Nullable annotations - Kotlin's nullable type system (?) handles this
- JSpecify annotations are primarily useful for Java code that will be consumed by Kotlin
- The annotations help Kotlin understand Java nullability when Java code is called from Kotlin

## Build Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests "TestClassName"

# Run the application
./gradlew bootRun

# Clean build artifacts
./gradlew clean

# Generate GraphQL client code
./gradlew generateJava
```

## Project Architecture

This is a reactive Spring Boot 3.5.3 application with Kotlin 1.9.25 that combines:
- **GraphQL API** via Netflix DGS framework
- **Reactive data access** with Spring Data Cassandra Reactive
- **Server-side rendering** using JTE templates
- **Dynamic UI updates** with HTMX
- **Cloud-native features** including Spring Cloud, Actuator, and Prometheus metrics

### Key Package Structure
- Base package: `dev.fzymgc.bootstrap.graphqlmodelmutate.bootstrap.graphqlmodelmutate`
- Main application class: `BootstrapGraphqlModelMutateApplication.kt`
- GraphQL schemas: `src/main/resources/graphql-client/`
- JTE templates: `src/main/jte/`
- Generated GraphQL code: `dev.fzymgc.bootstrap.graphqlmodelmutate.bootstrap.graphqlmodelmutate.codegen`

### Testing Strategy
- Integration tests use Testcontainers with Cassandra
- Test configuration in `TestcontainersConfiguration.kt`
- Reactive testing with Reactor Test and Kotlin Coroutines Test
- GraphQL testing with DGS test utilities

### Development Environment
- Java 21 toolchain required
- Docker Compose configuration starts Cassandra on port 9042
- JTE development mode enabled for template hot reload
- Spring Boot DevTools for application hot reload

## Key Technologies

- **Spring WebFlux**: Reactive web framework
- **Netflix DGS**: GraphQL framework with code generation
- **Cassandra**: NoSQL database (both regular and reactive repositories)
- **JTE + HTMX**: Server-side rendering with dynamic updates
- **Resilience4j**: Circuit breaker pattern implementation
- **Micrometer + Prometheus**: Metrics and observability
- **GraalVM**: Native image support configured