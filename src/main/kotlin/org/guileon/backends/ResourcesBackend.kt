package org.guileon.backends

import org.guileon.data.*
import javax.inject.Singleton

fun LearningResourceType.pname() = this.name.replace('_',' ')

@Singleton
class ResourcesBackend {
    fun getResource(resourceSlug: String) =
            LearningResource(
                    LearningResourceType.book,
                    "The Art of Computer Programming",
                    "book-the-art-of-computer-programming",
                    "https://www.amazon.es/Art-Computer-Programming-Sorting-Searching/dp/0201896850/",
                    "en",
                    512,
                    24,
                    "https://images-na.ssl-images-amazon.com/images/I/41eCbcQARTL._SX342_BO1,204,203,200_.jpg"
            )

    fun getRequirementsForResource(resourceSlug: String) = listOf(
            ProficencyRequirement(
                    Subject("Programming Languages", "programming-languages"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Algorithms and Data Structures", "algorithms-datastructures"),
                    ProficencyLevel.expert
            )
    )

    fun getMetadataForResource(resourceSlug: String): ResourceMetadata =
            BookMetadata(
                    "Donald E. Knuth",
                    1998,
                    "978-0201896855"
            )
}

