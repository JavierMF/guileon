package org.guileon.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.views.View
import org.guileon.backends.CareersBackend
import org.guileon.data.ProficencyRequirement
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
                        name = career.name,
                        requirements = requirements
                )
        )
    }
}