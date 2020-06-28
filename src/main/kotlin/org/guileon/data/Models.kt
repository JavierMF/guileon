package org.guileon.data

data class Career(
        val name: String,
        val slug: String
)

data class Subject(
        val name: String,
        val slug: String
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
        val slug: String,
        val url: String,
        val language: String, // ISO 639
        val likes: Int,
        val dislikes: Int,
        val imageUrl: String? = "https://picsum.com/220/290"
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
        val year: Int?,
        val ISBN: String?
): ResourceMetadata {
    override fun asUIString() = "by $author, $year. ISBN: $ISBN"
}
