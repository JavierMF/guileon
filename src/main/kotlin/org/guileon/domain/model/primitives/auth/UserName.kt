package org.guileon.domain.model.primitives.auth

import org.javiermf.primitives.exceptions.message

data class UserName(private val userName: String) {

    init {
        require( userName.length in 2..30 ) { message("User name length invalid. Must be between 2 and 30 characters.") }
        require( USERNAME_REGEX.matches(userName) ) { message("Invalid characters in user name. Only letters, digits and underscore allowed.") }
    }

    val value get() = userName

    override fun toString() = value

    companion object {
        val USERNAME_REGEX = "[a-zA-Z0-9_]".toRegex()
    }
}