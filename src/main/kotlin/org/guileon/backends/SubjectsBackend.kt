package org.guileon.backends

import org.guileon.data.*
import javax.inject.Singleton

@Singleton
class SubjectsBackend {
    fun getTopSubjects() = listOf(
            Subject("Software Development Practices", "sw-devel-practices"),
            Subject("Matlab", "matlab"),
            Subject("Graph Databases", "graph-databases"),
            Subject("Functional Programming", "func-prog"),
            Subject("Kubernetes", "kubernetes"),
            Subject("Multiplatform Mobile Development", "mutiplatform-mobile"),
            Subject("Unity", "unity")
    )

    fun getSubject(subjectSlub: String) =
            Subject("Programming Languages", "programming-languages")

    fun getRequirementsForSubjectLevel(subjectSlug: String, proficencyLevel: ProficencyLevel) = listOf(
            ProficencyRequirement(
                    Subject("Programming Languages", "programming-languages"),
                    ProficencyLevel.apprentice
            ),
            ProficencyRequirement(
                    Subject("OO Languages", "oo-languages"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Algorithms and Data Structures", "algorithms-datastructures"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Functional Languages", "functional-languages"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Software Compilers", "software-compilers"),
                    ProficencyLevel.apprentice
            )
    )

    fun getResourcesForSubjectLevel(subjectSlug: String, proficencyLevel: ProficencyLevel): List<LearningResource> = listOf(
            LearningResource(
                    LearningResourceType.online_course,
                    "Programming Languages from MIT",
                    "course-programming-languages-from-mit",
                    "https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-821-programming-languages-fall-2002/",
                    "en",
                    628,
                    3
            ),
            LearningResource(
                    LearningResourceType.book,
                    "The Art of Computer Programming",
                    "book-the-art-of-computer-programming",
                    "https://www.amazon.es/Art-Computer-Programming-Sorting-Searching/dp/0201896850/",
                    "en",
                    512,
                    24
            ),
            LearningResource(
                    LearningResourceType.talk,
                    "Advanced C Code Programming for Fun",
                    "talk-advanced-c-code-programming-for-fun",
                    "https://www.youtube.com/watch?v=BEQ3sRakIs0",
                    "en",
                    487,
                    32
            ),
            LearningResource(
                    LearningResourceType.blog_post,
                    "Heavy-light decompositon — it can be simple!",
                    "blogpost-hevylight-decomposition-simple",
                    "https://codeforces.com/blog/entry/12239",
                    "en",
                    325,
                    12
            )
    )
}
