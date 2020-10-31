package org.guileon.usecases

import org.guileon.boundaries.backends.persistence.TheRepository
import org.guileon.domain.model.BookMetadata
import org.guileon.domain.model.LearningResourceType
import org.guileon.domain.model.ResourceMetadata
import org.guileon.domain.model.primitives.names.AnyName
import org.javiermf.primitives.datetime.Year
import org.javiermf.primitives.isbn.Isbn
import org.javiermf.primitives.slug.Slug
import javax.inject.Inject
import javax.inject.Singleton

fun LearningResourceType.pname() = this.name.replace('_',' ')

@Singleton
class ResourcesBackend @Inject constructor(
        private val repository: TheRepository
) {
    fun getResource(resourceSlug: Slug) = repository.getResource(resourceSlug)

    fun getRequirementContributingResource(resourceSlug: Slug) =
            repository.getRequirementContributingResource(resourceSlug)

    fun getMetadataForResource(resourceSlug: Slug, type: LearningResourceType): ResourceMetadata? {
        val metadataMap = repository.getResourceMetadata(resourceSlug)
        return when (type) {
            LearningResourceType.book -> BookMetadata(
                    author = (metadataMap["author"] as String?)?.let { AnyName(it) },
                    year =  Year(metadataMap["year"].let { Integer.valueOf(it.toString()) }),
                    ISBN = (metadataMap["ISBN"] as String?)?.let { Isbn(it) }
            )
            else -> null
        }
    }
}

