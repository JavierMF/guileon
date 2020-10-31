package org.guileon.boundaries.clients.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/auth")
class AuthController {

    @Get("/login")
    @View("login")
    fun auth(): Map<String, Any> {
        return HashMap()
    }

    @Get("/loginFailed")
    @View("login")
    fun authFailed(): Map<String, Any> {
        return mapOf(Pair("errors", true))
    }
}