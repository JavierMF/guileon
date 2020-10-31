package org.guileon.usecases

import org.guileon.boundaries.backends.persistence.TheRepository
import org.guileon.domain.model.LearningResource
import org.guileon.domain.model.ProficencyLevel
import org.javiermf.primitives.slug.Slug
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectsBackend @Inject constructor(
        private val repository: TheRepository
) {
    // TODO: Return only top subjects
    fun getTopSubjects() = repository.getAllSubjects()

    fun getSubject(subjectSlub: Slug) = repository.getSubject(subjectSlub)

    fun getRequirementsForSubjectLevel(subjectSlug: Slug, proficencyLevel: ProficencyLevel) =
            repository.getRequirementsForSubjectLevel(subjectSlug, proficencyLevel)

    fun getResourcesForSubjectLevel(subjectSlug: Slug, proficencyLevel: ProficencyLevel): List<LearningResource> =
            repository.getResourcesForSubjectLevel(subjectSlug, proficencyLevel)

}
