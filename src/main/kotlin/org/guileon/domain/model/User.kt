package org.guileon.domain.model

import org.guileon.domain.model.primitives.auth.UserName
import org.guileon.domain.model.primitives.names.AnyName
import org.javiermf.primitives.email.Email

data class Person(
        val name: AnyName,
        val email: Email
)

data class User(
        val userName: UserName,
        val person: Person
)