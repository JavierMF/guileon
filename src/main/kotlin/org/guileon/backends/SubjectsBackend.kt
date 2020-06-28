package org.guileon.backends

import org.guileon.data.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectsBackend @Inject constructor(
        private val repository: TheRepository
) {
    // TODO: Return only top subjects
    fun getTopSubjects() = repository.getAllSubjects()

    fun getSubject(subjectSlub: String) = repository.getSubject(subjectSlub)

    fun getRequirementsForSubjectLevel(subjectSlug: String, proficencyLevel: ProficencyLevel) =
            repository.getRequirementsForSubjectLevel(subjectSlug, proficencyLevel)

    fun getResourcesForSubjectLevel(subjectSlug: String, proficencyLevel: ProficencyLevel): List<LearningResource> =
            repository.getResourcesForSubjectLevel(subjectSlug, proficencyLevel)

}
