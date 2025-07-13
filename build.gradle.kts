plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.netflix.dgs.codegen)
    alias(libs.plugins.graalvm.native)
    alias(libs.plugins.jte.gradle)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
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

dependencies {
    // Spring Boot bundles
    implementation(libs.bundles.spring.boot.web)
    implementation(libs.bundles.spring.boot.data)

    // Individual Spring dependencies
    implementation(libs.spring.cloud.starter.circuitbreaker.reactor.resilience4j)

    // Jackson
    implementation(libs.jackson.module.kotlin)

    // Netflix DGS
    implementation(libs.graphql.dgs.spring.graphql.starter)

    // JTE
    implementation(libs.jte.spring.boot.starter)

    // Micrometer
    implementation(libs.micrometer.tracing.bridge.brave)
    runtimeOnly(libs.micrometer.registry.prometheus)

    // Kotlin
    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.kotlin.reactive)

    // Other libraries
    implementation(libs.jspecify)
    compileOnly(libs.lombok)

    // Development dependencies
    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.docker.compose)

    // Annotation processors
    annotationProcessor(libs.spring.boot.configuration.processor)
    annotationProcessor(libs.lombok)

    // Test dependencies
    testImplementation(libs.bundles.testing)
    testImplementation(libs.graphql.dgs.spring.graphql.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

dependencyManagement {
    imports {
        mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${libs.versions.netflixDgs.get()}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.springCloudVersion.get()}")
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
    version.set(libs.versions.ktlintCore.get())
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
