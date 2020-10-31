package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import org.guileon.boundaries.clients.web.providers.AuthViewDataProvider
import org.guileon.boundaries.clients.web.providers.AuthViewModel
import org.guileon.domain.model.ProficencyLevel
import org.guileon.domain.model.ProficencyRequirement
import org.guileon.usecases.pname
import org.guileon.usecases.SubjectsBackend
import org.javiermf.primitives.slug.Slug
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

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/subjects")
class SubjectsController @Inject constructor(
        private val subjectsBackend: SubjectsBackend,
        private val authViewDataProvider: AuthViewDataProvider
) {

    @Get("{subjectSlug}/{proficencyLevel}")
    @View("subject-proficency")
    fun subjectProficency(
            @PathVariable subjectSlug: String,
            @PathVariable proficencyLevel: ProficencyLevel
    ): HttpResponse<AuthViewModel<SubjectProficencyViewModel>> {
        val slug = Slug(subjectSlug)
        // TODO: All of these in a query?
        val subject = subjectsBackend.getSubject(slug)
        val requirements = subjectsBackend.getRequirementsForSubjectLevel(slug, proficencyLevel)
        val resources = subjectsBackend.getResourcesForSubjectLevel(slug, proficencyLevel)
                .map { LearningResourceViewModel(it.name.value, it.slug.value, it.type.pname(), it.likes.value) }

        val view = SubjectProficencyViewModel(
                name = subject?.name?.value ?: "",
                proficencyLevel = proficencyLevel,
                requirements = requirements,
                resources = resources
        )

        return HttpResponse.ok(authViewDataProvider.addAuthInfo(view))
    }
}