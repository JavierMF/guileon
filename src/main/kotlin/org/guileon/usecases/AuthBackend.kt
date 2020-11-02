package org.guileon.usecases

import org.guileon.boundaries.backends.persistence.TheRepository
import org.guileon.domain.model.primitives.auth.ClearPassword
import org.guileon.domain.model.primitives.auth.UserName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthBackend @Inject constructor(
        private val repository: TheRepository
) {
    fun isAuthValidForUser(user: UserName, secret: ClearPassword): Boolean {
        val authData = repository.getAuthDataFor(user)
        return authData?.isValidPassword(secret) ?: false
    }
}

