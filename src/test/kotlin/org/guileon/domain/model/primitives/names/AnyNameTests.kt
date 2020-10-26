package org.guileon.domain.model.primitives.names

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AnyNameTests {

    private fun assertValid(name: String) = Assertions.assertNotNull(AnyName(name))
    private fun assertInvalid(name: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) { AnyName(name) }
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "C",
        "The Art of Computer Programming",
        "Donald Knuth",
        "GOTO 2020 • The Pragmatic Programmer 20 Years Later • Pragmatic Dave Thomas",
        "KotlinConf 2019: Kotlin Puzzlers, Vol 3 by Anton Keks",
        "Dan Johnsson, Daniel Deogun, Daniel Sawano",
        "Securing DevOps-Safe services in the Cloud: Security in the Cloud"
    ])
    fun `valid | valid hash`(name : String) = assertValid(name)

    @ParameterizedTest
    @ValueSource(strings = [
        "",
        " ",
        "a name\nsecond name",
        "   weird format",
        "weird format  "
    ])
    fun `invalid | invalid name`(name : String) = assertInvalid(name)

    @Test
    fun `invalid | extreme length`() = assertInvalid("aa".repeat(100000))

    @Test
    fun `equal hash`() = Assertions.assertTrue(AnyName("Donald Knuth") == AnyName("Donald Knuth"))

    @Test
    fun `string hash`() = Assertions.assertEquals("Donald Knuth", "${AnyName("Donald Knuth")}")

}