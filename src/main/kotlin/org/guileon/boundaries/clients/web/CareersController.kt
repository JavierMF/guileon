package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.views.View
import org.guileon.usecases.CareersBackend
import org.guileon.domain.model.ProficencyRequirement
import javax.inject.Inject

data class CareerViewModel(
        val name: String,
        val requirements: List<ProficencyRequirement>
)

@Controller("/careers")
class CareersController @Inject constructor(
        private val careersBackend: CareersBackend
){

    @Get("{careerSlug}")
    @View("career")
    fun career(@PathVariable careerSlug: String): HttpResponse<CareerViewModel> {
        val career = careersBackend.getCareer(careerSlug)
        val requirements = careersBackend.getCareersRequirements(careerSlug)
        return HttpResponse.ok(
                CareerViewModel(
                        name = career?.name?.value ?: "Career not found",
                        requirements = requirements
                )
        )
    }
}