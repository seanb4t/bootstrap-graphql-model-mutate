# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Important Git Workflow

**CRITICAL**: Use a PR (Pull Request) for all features and changes. Do NOT ever commit directly to main.

### Workflow Steps:
1. Always create a feature branch from main
2. Make changes in the feature branch
3. Create a pull request to merge changes
4. Never push directly to main branch

Example workflow:
```bash
# Create and switch to a new branch
git checkout -b feature/your-feature-name

# Make your changes and commit
git add .
git commit -m "Your commit message"

# Push the branch
git push origin feature/your-feature-name

# Then create a PR on GitHub
```

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

## Code Quality and Linting

This project uses Detekt for static code analysis and Ktlint for code formatting.

### Ktlint (Code Formatting)
- **Version**: 1.5.0 via org.jlleitschuh.gradle.ktlint plugin
- **Configuration**: See `.editorconfig` file for formatting rules
- **Style**: Official Kotlin style guide with 120 character line length

### Detekt (Static Analysis)
- **Version**: 1.23.8
- **Configuration**: See `detekt.yml` for detailed rule configuration
- **Note**: Compatible with Kotlin 2.0.21

### Linting Commands
```bash
# Format all Kotlin code
./gradlew formatKotlin

# Check code formatting (without fixing)
./gradlew ktlintCheck

# Run all code quality checks
./gradlew checkCodeQuality

# Run Detekt analysis only
./gradlew detekt
```

### Key Rules
- Maximum line length: 120 characters
- Indentation: 4 spaces
- Kotlin official style guide
- Trailing commas allowed
- Generated code is excluded from checks

### Suppressing Warnings
- For Ktlint: Use `@Suppress("ktlint:rule-name")` or `// ktlint-disable rule-name`
- For Detekt: Use `@Suppress("DetektRuleName")` or configure in detekt.yml

## Build Commands

This project uses **Kotlin 2.0.21** with Java 21.

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