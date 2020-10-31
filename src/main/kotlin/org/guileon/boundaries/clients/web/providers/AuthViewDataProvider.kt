package org.guileon.boundaries.clients.web.providers

import io.micronaut.security.utils.SecurityService
import javax.inject.Inject
import javax.inject.Singleton

data class UserViewModel(val name: String?)

data class AuthViewModel<T> (
        val data: T,
        val user: UserViewModel
)

@Singleton
class AuthViewDataProvider @Inject constructor(
        private val securityService: SecurityService
) {

    fun <T> addAuthInfo(dataView: T): AuthViewModel<T> {
        return AuthViewModel(
                data = dataView,
                user = UserViewModel(
                        name = securityService.username().orElse(null)
                )
        )
    }
}