package dev.fzymgc.bootstrap.graphqlmodelmutate

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class BootstrapGraphqlModelMutateApplicationTests {

    @Test
    fun contextLoads() {
    }

}
