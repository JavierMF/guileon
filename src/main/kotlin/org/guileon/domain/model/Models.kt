package org.guileon.domain.model

import org.guileon.domain.model.primitives.names.AnyName
import org.guileon.domain.model.primitives.names.EntityName
import org.javiermf.primitives.datetime.Year
import org.javiermf.primitives.isbn.Isbn
import org.javiermf.primitives.lang.LangISO639
import org.javiermf.primitives.quantity.PositiveQuantity
import org.javiermf.primitives.slug.Slug
import org.javiermf.primitives.url.Url

data class Career(
        val name: EntityName,
        val slug: Slug
)

data class Subject(
        val name: EntityName,
        val slug: Slug
)

data class ProficencyRequirement(
        val subject: Subject,
        val level: ProficencyLevel
)

enum class ProficencyLevel {
    master,
    expert,
    apprentice
}

data class LearningResource(
        val type: LearningResourceType,
        val name: AnyName,
        val slug: Slug,
        val url: Url,
        val language: LangISO639,
        val likes: PositiveQuantity,
        val dislikes: PositiveQuantity
)

enum class LearningResourceType {
    book,
    talk,
    online_course,
    blog_post,
    website
}

interface ResourceMetadata {
    fun asUIString(): String
    fun imageUrl(): Url?
}

data class BookMetadata(
        val author: AnyName?,
        val year: Year?,
        val ISBN: Isbn?
): ResourceMetadata {
    override fun asUIString() = "by $author, $year. ISBN: $ISBN"
    override fun imageUrl() = Url("http://covers.openlibrary.org/b/isbn/$ISBN-L.jpg")
}
