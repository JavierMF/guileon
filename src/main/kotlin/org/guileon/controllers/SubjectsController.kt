package org.guileon.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.views.View
import org.guileon.data.ProficencyLevel
import org.guileon.data.ProficencyRequirement
import org.guileon.backends.pname
import org.guileon.backends.SubjectsBackend
import javax.inject.Inject

data class SubjectProficencyViewModel(
        val name: String,
        val proficencyLevel: ProficencyLevel,
        val requirements: List<ProficencyRequirement>,
        val resources: List<LearningResourceViewModel>
)

data class LearningResourceViewModel(val name: String, val slug: String, val type: String)

@Controller("/subjects")
class SubjectsController @Inject constructor(
        private val subjectsBackend: SubjectsBackend
) {

    @Get("{subjectSlug}/{proficencyLevel}")
    @View("subject-proficency")
    fun subjectProficency(
            @PathVariable subjectSlug: String,
            @PathVariable proficencyLevel: ProficencyLevel
    ): HttpResponse<SubjectProficencyViewModel> {
        val subject = subjectsBackend.getSubject(subjectSlug)
        val requirements = subjectsBackend.getRequirementsForSubjectLevel(subjectSlug, proficencyLevel)
        val resources = subjectsBackend.getResourcesForSubjectLevel(subjectSlug, proficencyLevel)
                .map { LearningResourceViewModel(it.name, it.slug, it.type.pname()) }
        return HttpResponse.ok(
                SubjectProficencyViewModel(
                        name = subject.name,
                        proficencyLevel = proficencyLevel,
                        requirements = requirements,
                        resources = resources
                )
        )
    }
}