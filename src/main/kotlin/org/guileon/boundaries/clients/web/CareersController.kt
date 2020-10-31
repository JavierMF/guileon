package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import org.guileon.boundaries.clients.web.providers.AuthViewDataProvider
import org.guileon.boundaries.clients.web.providers.AuthViewModel
import org.guileon.usecases.CareersBackend
import org.guileon.domain.model.ProficencyRequirement
import org.javiermf.primitives.slug.Slug
import javax.inject.Inject

data class CareerViewModel(
        val name: String,
        val requirements: List<ProficencyRequirement>
)

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/careers")
class CareersController @Inject constructor(
        private val careersBackend: CareersBackend,
        private val authViewDataProvider: AuthViewDataProvider
){

    @Get("{careerSlug}")
    @View("career")
    fun career(@PathVariable careerSlug: String): HttpResponse<AuthViewModel<CareerViewModel>> {
        val slug = Slug(careerSlug)
        val career = careersBackend.getCareer(slug)
        val requirements = careersBackend.getCareersRequirements(slug)
        val view = CareerViewModel(
                name = career?.name?.value ?: "Career not found",
                requirements = requirements
        )
        return HttpResponse.ok(authViewDataProvider.addAuthInfo(view))
    }
}