package org.guileon.domain.model.primitives.auth

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ClearPasswordTests {

    private fun assertValid(password: String) = Assertions.assertNotNull(ClearPassword(password))
    private fun assertInvalid(password: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) { ClearPassword(password) }
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "guileon",
        "guile",
        "TheMegaPass&ord0fTheFutur3./",
        "-----"
    ])
    fun `valid | valid password`(password : String) = assertValid(password)

    @ParameterizedTest
    @ValueSource(strings = [
        "",
        "1234",
        "123456789012345678901234567890X",
        " password",
        "password ",
        "pass word",
        "      "
    ])
    fun `invalid | invalid password`(password : String) = assertInvalid(password)

    @Test
    fun `invalid | extreme length`() = assertInvalid("aa".repeat(100000))

    @Test
    fun `equal password`() = Assertions.assertTrue(ClearPassword("guileon") == ClearPassword("guileon"))

    @Test
    fun `string password`() = Assertions.assertEquals("*****", "${ClearPassword("guileon")}")

    @Test
    fun `sha1 password`() = Assertions.assertEquals("62e247fe88240fa9848fd09846162fce395b018a", ClearPassword("guileon").toSHA1Hash().value)
}