package org.guileon.domain.model.primitives.names

import org.javiermf.primitives.exceptions.message

data class AnyName(private val name: String) {

    init {
        require( name.length < 200 ) { message("Name length must be less than 200 characters") }
        require( name.isNotBlank() ) { message("Name can not be empty") }
        require( name.trim().length == name.length ) { message("Name can not start or end with blanks")}
        require( RESOURCE_NAME_REGEX.matches(name) ) { message("Name does not follow valid format") }
    }

    val value get() = name
    override  fun toString() = value

    companion object {
        val RESOURCE_NAME_REGEX = "^[^\\s]?[^\\n]*[^\\s]$".toRegex()
    }
}