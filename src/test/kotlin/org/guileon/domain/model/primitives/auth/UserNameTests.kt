package org.guileon.domain.model.primitives.auth

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UserNameTests {

    private fun assertValid(name: String) = Assertions.assertNotNull(UserName(name))
    private fun assertInvalid(name: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) { UserName(name) }
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "guileon",
        "gui",
        "Guileon2020",
        "__guileon__"
    ])
    fun `valid | valid name`(name : String) = assertValid(name)

    @ParameterizedTest
    @ValueSource(strings = [
        "",
        " guileon",
        "guileon ",
        "guile\non",
        "gui-leon",
        "gui;leon"
    ])
    fun `invalid | invalid name`(name : String) = assertInvalid(name)

    @Test
    fun `invalid | extreme length`() = assertInvalid("aa".repeat(100000))

    @Test
    fun `equal name`() = Assertions.assertTrue(UserName("guileon") == UserName("guileon"))

    @Test
    fun `string name`() = Assertions.assertEquals("guileon", "${UserName("guileon")}")

}