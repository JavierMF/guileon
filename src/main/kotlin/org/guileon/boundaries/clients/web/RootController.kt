package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import org.guileon.boundaries.clients.web.providers.AuthViewDataProvider
import org.guileon.boundaries.clients.web.providers.AuthViewModel
import org.guileon.domain.model.Subject
import org.guileon.usecases.SubjectsBackend
import org.guileon.domain.model.Career
import org.guileon.usecases.CareersBackend
import java.security.Principal
import javax.inject.Inject

data class RootViewModel(
        val careers: List<Career>,
        val subjects: List<Subject>
)

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
class RootController @Inject constructor(
        private val careersBackend: CareersBackend,
        private val subjectsBackend: SubjectsBackend,
        private val authViewDataProvider: AuthViewDataProvider
) {

    @Get("/")
    @View("root")
    fun index(): HttpResponse<AuthViewModel<RootViewModel>> {
        val view = RootViewModel(
                careers = careersBackend.getTopCareers(),
                subjects = subjectsBackend.getTopSubjects()
        )
        return HttpResponse.ok(authViewDataProvider.addAuthInfo(view))
    }
}