package org.guileon

import io.kotlintest.specs.StringSpec
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class AppTest(private val application: EmbeddedApplication<*>): StringSpec({

    "test the server is running" {
        assert(application.isRunning)
    }
})
