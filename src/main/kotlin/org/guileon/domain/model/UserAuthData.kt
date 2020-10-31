package org.guileon.domain.model

import org.guileon.domain.model.primitives.auth.ClearPassword
import org.guileon.domain.model.primitives.auth.UserName
import org.javiermf.primitives.auth.SHA1Hash

data class UserAuthData(
        val userName: UserName,
        val passwordHash: SHA1Hash
) {
    fun isValidPassword(password: ClearPassword) =
        password.toSHA1Hash() == passwordHash
}

