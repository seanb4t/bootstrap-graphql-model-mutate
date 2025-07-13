package dev.fzymgc.bootstrap.graphqlmodelmutate

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.CassandraContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
    @Bean
    @ServiceConnection
    fun cassandraContainer(): CassandraContainer<*> = CassandraContainer(DockerImageName.parse("cassandra:latest"))
}
