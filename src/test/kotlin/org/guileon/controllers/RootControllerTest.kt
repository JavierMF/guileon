package org.guileon.controllers

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class RootControllerTest (
        @Client("/") private val client: RxHttpClient
): StringSpec({

    /*
    "return hello world" {
        val body = client.retrieve("/").firstElement().blockingGet()
        body.shouldBe("Hello World")
    }
    */
})