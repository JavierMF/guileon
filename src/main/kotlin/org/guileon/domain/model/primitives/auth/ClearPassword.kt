package org.guileon.domain.model.primitives.auth

import org.javiermf.primitives.auth.SHA1Hash
import org.javiermf.primitives.exceptions.message
import java.nio.charset.Charset
import java.security.MessageDigest

data class ClearPassword(private val password: String) {

    init {
        require( password.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH ) { message("User name length invalid. Must be between $MIN_PASSWORD_LENGTH and $MAX_PASSWORD_LENGTH characters.") }
        require( PASSWORD_REGEX.matches(password) ) { message("Password can not contain blank spaces.") }
    }

    override fun toString() = "*****"

    fun toSHA1Hash(): SHA1Hash {
        val secretHashBytes = MessageDigest.getInstance("SHA-1").digest(password.toByteArray(Charset.defaultCharset()))
        return SHA1Hash(secretHashBytes.joinToString("") { "%02x".format(it) })
    }

    companion object {
        val MIN_PASSWORD_LENGTH = 5
        val MAX_PASSWORD_LENGTH = 30
        val PASSWORD_REGEX = "[^\\s]+".toRegex()
    }
}