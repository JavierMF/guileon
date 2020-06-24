package org.guileon.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.views.View
import org.guileon.data.Subject
import org.guileon.backends.SubjectsBackend
import org.guileon.data.Career
import org.guileon.backends.CareersBackend
import javax.inject.Inject

data class RootViewModel(
        val careers: List<Career>,
        val subjects: List<Subject>
)

@Controller("/")
class RootController @Inject constructor(
        private val careersBackend: CareersBackend,
        private val subjectsBackend: SubjectsBackend
) {

    @Get("/")
    @View("root")
    fun index(): HttpResponse<RootViewModel> {
        return HttpResponse.ok(
                RootViewModel(
                        careers = careersBackend.getTopCareers(),
                        subjects = subjectsBackend.getTopSubjects()
                )
        )
    }
}