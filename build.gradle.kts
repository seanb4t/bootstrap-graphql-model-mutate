plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.spring") version "2.0.21"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.netflix.dgs.codegen") version "7.0.3"
    id("org.graalvm.buildtools.native") version "0.10.6"
    id("gg.jte.gradle") version "3.1.16"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "13.0.0"
}

group = "dev.fzymgc.bootstrap.graphql-model-mutate"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["netflixDgsVersion"] = "10.2.1"
extra["springCloudVersion"] = "2025.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-cassandra")
    implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("gg.jte:jte-spring-boot-starter-3:3.1.16")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    implementation("org.jspecify:jspecify:1.0.0")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:cassandra")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xnullability-annotations=@org.jspecify.annotations:strict",
        )
    }
}

tasks.generateJava {
    schemaPaths.add("$projectDir/src/main/resources/graphql-client")
    packageName = "dev.fzymgc.bootstrap.graphqlmodelmutate.codegen"
    generateClient = true
}

jte {
    generate()
    binaryStaticContent = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Detekt configuration
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.from(files("$projectDir/detekt.yml"))
    autoCorrect = false
    parallel = true
}

// Ktlint configuration
ktlint {
    version.set("1.5.0")
    android.set(false)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)
    additionalEditorconfig.set(
        mapOf(
            "indent_size" to "4",
            "max_line_length" to "120",
            "ktlint_code_style" to "ktlint_official",
            "ij_kotlin_allow_trailing_comma" to "true",
            "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
        ),
    )
    filter {
        exclude("**/build/**")
        exclude("**/generated/**")
        exclude("**/generated-sources/**")
        exclude("**/codegen/**")
        exclude("**/*.Generated.kt")
    }
}

// Configure ktlint tasks to depend on code generation
tasks.named("runKtlintCheckOverMainSourceSet") {
    mustRunAfter("generateJte", "generateJava")
}

tasks.named("runKtlintFormatOverMainSourceSet") {
    mustRunAfter("generateJte", "generateJava")
}

// Custom tasks for code quality
tasks.register("formatKotlin") {
    description = "Format Kotlin code using ktlint"
    group = "formatting"
    dependsOn("ktlintFormat")
}

tasks.register("checkCodeQuality") {
    description = "Run all code quality checks"
    group = "verification"
    dependsOn("ktlintCheck", "detekt")
}

// Configure check task to include linting
tasks.check {
    dependsOn("ktlintCheck", "detekt")
}

// Make build depend on code quality checks
tasks.build {
    dependsOn("checkCodeQuality")
}
