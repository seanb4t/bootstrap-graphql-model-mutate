package dev.fzymgc.bootstrap.graphqlmodelmutate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootstrapGraphqlModelMutateApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator") // Required for Spring Boot main method
    runApplication<BootstrapGraphqlModelMutateApplication>(*args)
}
