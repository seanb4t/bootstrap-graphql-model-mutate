package dev.fzymgc.bootstrap.graphqlmodelmutate

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<BootstrapGraphqlModelMutateApplication>().with(TestcontainersConfiguration::class).run(*args)
}
