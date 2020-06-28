package org.guileon.backends

import org.guileon.data.*
import javax.inject.Inject
import javax.inject.Singleton

fun LearningResourceType.pname() = this.name.replace('_',' ')

@Singleton
class ResourcesBackend @Inject constructor(
        private val repository: TheRepository
) {
    fun getResource(resourceSlug: String) = repository.getResource(resourceSlug)

    fun getRequirementContributingResource(resourceSlug: String) =
            repository.getRequirementContributingResource(resourceSlug)

    fun getMetadataForResource(resourceSlug: String, type: LearningResourceType): ResourceMetadata? {
        val metadataMap = repository.getResourceMetadata(resourceSlug)
        return when (type) {
            LearningResourceType.book -> BookMetadata(
                    author = metadataMap["author"] as String?,
                    year =  metadataMap["year"].let { Integer.valueOf(it.toString()) },
                    ISBN = metadataMap["ISBN"] as String?
            )
            else -> null
        }
    }
}

