package org.guileon.domain.model.primitives.names

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EntityNameTests {
    private fun assertValid(name: String) = Assertions.assertNotNull(EntityName(name))
    private fun assertInvalid(name: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) { EntityName(name) }
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "C",
        "C++",
        "C#",
        "Programming Languages",
        "Arts & Crafts",
        "Algorithms, Data Structures & More Stuff",
        "One-Other"
    ])
    fun `valid | valid hash`(name : String) = assertValid(name)

    @ParameterizedTest
    @ValueSource(strings = [
        "",
        " ",
        "<HTML>",
        "whatever; drop database;",
        "A subject (or two)",
        "  weird subject",
        "weird subject  "
    ])
    fun `invalid | invalid name`(name : String) = assertInvalid(name)

    @Test
    fun `invalid | extreme length`() = assertInvalid("aa".repeat(100000))

    @Test
    fun `equal hash`() = Assertions.assertTrue(EntityName("Cloud Architect") == EntityName("Cloud architect"))

    @Test
    fun `string hash`() = Assertions.assertEquals("Cloud Architect", "${EntityName("Cloud Architect")}")

}