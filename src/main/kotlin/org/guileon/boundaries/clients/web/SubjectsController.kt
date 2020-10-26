package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.views.View
import org.guileon.domain.model.ProficencyLevel
import org.guileon.domain.model.ProficencyRequirement
import org.guileon.usecases.pname
import org.guileon.usecases.SubjectsBackend
import javax.inject.Inject

data class SubjectProficencyViewModel(
        val name: String,
        val proficencyLevel: ProficencyLevel,
        val requirements: List<ProficencyRequirement>,
        val resources: List<LearningResourceViewModel>
)

data class LearningResourceViewModel(
        val name: String,
        val slug: String,
        val type: String,
        val likes: Int
)

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
        // TODO: All of these in a query?
        val subject = subjectsBackend.getSubject(subjectSlug)
        val requirements = subjectsBackend.getRequirementsForSubjectLevel(subjectSlug, proficencyLevel)
        val resources = subjectsBackend.getResourcesForSubjectLevel(subjectSlug, proficencyLevel)
                .map { LearningResourceViewModel(it.name.value, it.slug.value, it.type.pname(), it.likes.value) }

        return HttpResponse.ok(
                SubjectProficencyViewModel(
                        name = subject?.name?.value ?: "",
                        proficencyLevel = proficencyLevel,
                        requirements = requirements,
                        resources = resources
                )
        )
    }
}