package org.guileon.domain.model

import org.guileon.domain.model.primitives.auth.ClearPassword
import org.guileon.domain.model.primitives.auth.UserName
import org.javiermf.primitives.auth.SHA1Hash
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserAuthDataTests {

    @Test
    fun `valid password`() = Assertions.assertTrue(
            UserAuthData(
                    UserName("any_name"),
                    SHA1Hash("62e247fe88240fa9848fd09846162fce395b018a")
            ).isValidPassword(ClearPassword("guileon"))
    )

    @Test
    fun `invalid password`() = Assertions.assertFalse(
            UserAuthData(
                    UserName("any_name"),
                    SHA1Hash("62e247fe88240fa9848fd09846162fce395b018a")
            ).isValidPassword(ClearPassword("this_is_wrong"))
    )
}