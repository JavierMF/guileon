package org.guileon.domain.model

import org.javiermf.primitives.datetime.Year
import org.javiermf.primitives.isbn.Isbn
import org.javiermf.primitives.lang.LangISO639
import org.javiermf.primitives.quantity.PositiveQuantity
import org.javiermf.primitives.slug.Slug
import org.javiermf.primitives.url.Url

data class Career(
        val name: String,
        val slug: Slug
)

data class Subject(
        val name: String,
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
        val name: String,
        val slug: Slug,
        val url: Url,
        val language: LangISO639,
        val likes: PositiveQuantity,
        val dislikes: PositiveQuantity,
        val imageUrl: Url? = Url("https://picsum.com/220/290")
)

enum class LearningResourceType {
    book,
    talk,
    online_course,
    blog_post,
    website
}

interface ResourceMetadata {
    fun asUIString(): String;
}

data class BookMetadata(
        val author: String?,
        val year: Year?,
        val ISBN: Isbn?
): ResourceMetadata {
    override fun asUIString() = "by $author, $year. ISBN: $ISBN"
}
