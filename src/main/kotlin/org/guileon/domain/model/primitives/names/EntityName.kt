package org.guileon.domain.model.primitives.names

import org.javiermf.primitives.exceptions.message

data class EntityName(private val name: String) {

    init {
        require( name.length < 50 ) { message("Name length must be less than 50 characters") }
        require( name.isNotBlank() ) { message("Name can not be empty") }
        require( name.trim().length == name.length ) { message("Name can not start or end with blanks")}
        require( ENTITY_NAME_REGEX.matches(name) ) { message("Name does not follow valid format")}
    }

    val value get() = name
    override  fun toString() = value

    override fun equals(other: Any?) =
            other is EntityName && other.hashCode() == this.hashCode()
    override fun hashCode() = value.toLowerCase().hashCode()

    companion object {
        val ENTITY_NAME_REGEX = "[\\w +#&,-]+".toRegex()
    }
}

