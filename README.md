# Bootstrap GraphQL Model Mutate

A modern, reactive Spring Boot application showcasing GraphQL APIs, reactive data access, and comprehensive code quality tools.

## 🚀 Features

- **Spring Boot 3.5.3** with **Kotlin 2.0.21**
- **Reactive Stack**: Spring WebFlux for non-blocking I/O
- **GraphQL API**: Netflix DGS framework with code generation
- **Database**: Apache Cassandra with reactive repositories
- **Templates**: JTE for server-side rendering
- **Null Safety**: JSpecify annotations for Java-Kotlin interoperability
- **Code Quality**: Detekt static analysis and Ktlint formatting
- **Cloud Native**: Spring Cloud, Actuator, and Prometheus metrics
- **Native Image**: GraalVM native image support

## 📋 Prerequisites

- Java 21 (GraalVM recommended for native image support)
- Docker & Docker Compose (for Cassandra)
- Gradle 8.x (wrapper included)

## 🛠️ Getting Started

### Clone the repository
```bash
git clone https://github.com/seanb4t/bootstrap-graphql-model-mutate.git
cd bootstrap-graphql-model-mutate
```

### Start Cassandra
```bash
docker-compose up -d
```

### Build the project
```bash
./gradlew build
```

### Run the application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## 🧪 Testing

### Run all tests
```bash
./gradlew test
```

### Run integration tests with Testcontainers
```bash
./gradlew integrationTest
```

## 🔍 Code Quality

### Format code with Ktlint
```bash
./gradlew formatKotlin
```

### Check code quality
```bash
./gradlew checkCodeQuality
```

### Run Detekt analysis only
```bash
./gradlew detekt
```

## 📊 GraphQL

### GraphQL Playground
Access the GraphQL playground at: `http://localhost:8080/graphql`

### Generate GraphQL client code
```bash
./gradlew generateJava
```

Generated code will be in: `src/main/generated`

## 🏗️ Project Structure

```
src/
├── main/
│   ├── kotlin/         # Kotlin source files
│   ├── java/           # Java source files (JSpecify examples)
│   ├── jte/            # JTE templates
│   └── resources/      # Configuration files
│       └── graphql-client/  # GraphQL schemas
└── test/
    └── kotlin/         # Test files
```

## 🔧 Configuration

### Key Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Kotlin | 2.0.21 | Primary language with K2 compiler |
| Spring Boot | 3.5.3 | Application framework |
| Spring WebFlux | 6.x | Reactive web framework |
| Netflix DGS | 10.2.1 | GraphQL framework |
| Cassandra | Latest | NoSQL database |
| JTE | 3.1.16 | Template engine |
| JSpecify | 1.0.0 | Null-safety annotations |
| Detekt | 1.23.8 | Static code analysis |
| Ktlint | 1.5.0 | Code formatting |

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `CASSANDRA_HOST` | Cassandra host | localhost |
| `CASSANDRA_PORT` | Cassandra port | 9042 |
| `SERVER_PORT` | Application port | 8080 |

## 📐 Code Style

This project follows the official Kotlin coding conventions with:
- Max line length: 120 characters
- Indentation: 4 spaces
- Trailing commas allowed

See `.editorconfig` and `detekt.yml` for detailed configuration.

## 🚦 CI/CD

GitHub Actions workflow runs on all pushes and PRs to main branch:
- Builds the project
- Runs Ktlint checks
- Runs all tests
- Uploads test results

## 📝 Documentation

- [CLAUDE.md](CLAUDE.md) - Detailed guidance for AI assistants and developers
- [GraphQL Schema](src/main/resources/graphql-client/) - API schema definitions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Run code formatting (`./gradlew formatKotlin`)
4. Commit your changes (`git commit -m 'Add amazing feature'`)
5. Push to the branch (`git push origin feature/amazing-feature`)
6. Open a Pull Request

### Pre-commit checklist
- [ ] Code is formatted with Ktlint
- [ ] All tests pass
- [ ] Detekt analysis shows no issues
- [ ] Documentation is updated

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot team for the excellent reactive framework
- Netflix for the DGS GraphQL framework
- JetBrains for Kotlin and development tools
- The open-source community for all the amazing libraries

## 📞 Support

For questions and support:
- Open an issue on GitHub
- Check existing issues for solutions
- Review the [CLAUDE.md](CLAUDE.md) file for detailed technical guidance

---

Built with ❤️ using Spring Boot and Kotlin