package org.guileon.boundaries.backends.persistence

import org.guileon.domain.model.*
import org.guileon.domain.model.primitives.auth.UserName
import org.guileon.domain.model.primitives.names.AnyName
import org.guileon.domain.model.primitives.names.EntityName
import org.javiermf.primitives.auth.SHA1Hash
import org.javiermf.primitives.lang.LangISO639
import org.javiermf.primitives.quantity.PositiveQuantity
import org.javiermf.primitives.slug.Slug
import org.javiermf.primitives.url.Url
import org.neo4j.driver.Value
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TheRepository @Inject constructor(
        private val neoDb: NeoDb
){
    fun getAllCareers(): List<Career> =
            neoDb.readAsList("MATCH (c:Career) RETURN c") { Career(it) }

    fun getAllSubjects(): List<Subject> =
            neoDb.readAsList("MATCH (s:Subject) RETURN s") { Subject(it) }

    fun getCareer(careerSlug: Slug): Career?  =
            neoDb.readAsEntity(
                    "MATCH (c:Career {slug: \$careerSlug}) RETURN c",
                    mapOf("careerSlug" to careerSlug.value)
            ) { Career(it) }

    fun getCareersRequirements(careerSlug: Slug): List<ProficencyRequirement> =
        neoDb.readListAndMap(
                "MATCH (Career {slug : \$careerSlug}) -[:REQUIRES_PROFICENCY]-> (sp:SubjectProficency) -[:FOR_SUBJECT]-> (s:Subject) RETURN s,sp",
                mapOf("careerSlug" to careerSlug.value)
        ){ ProficencyRequirement(it["s"], it["sp"]) }

    fun getResource(resourceSlug: Slug): LearningResource? =
            neoDb.readAsEntity(
                    "MATCH (c:LearningResource {slug: \$resourceSlug}) RETURN c",
                    mapOf("resourceSlug" to resourceSlug.value)
            ) { LearningResource(it) }

    fun getResourceMetadata(resourceSlug: Slug): Map<String, Any> =
            neoDb.readAsEntity(
                    "MATCH (LearningResource {slug : \$resourceSlug}) - [tr:METADATA]-> (m:Metadata) RETURN m",
                    mapOf("resourceSlug" to resourceSlug.value)
            ) {
                it.asMap()
            } ?: emptyMap()

    fun getSubject(subjectSlug: Slug): Subject? =
        neoDb.readAsEntity(
                "MATCH (c:Subject {slug: \$subjectSlug}) RETURN c",
                mapOf("subjectSlug" to subjectSlug.value)
        ) { Subject(it) }

    fun getRequirementsForSubjectLevel(subjectSlug: Slug,  proficencyLevel: ProficencyLevel): List<ProficencyRequirement> =
            neoDb.readListAndMap(
                    "MATCH  (sp:SubjectProficency) -[:REQUIRES_PROFICENCY]-> (sp2:SubjectProficency) -[:FOR_SUBJECT]-> (s:Subject)\n" +
                            "WHERE (sp:SubjectProficency {level: \$level}) -[:FOR_SUBJECT]-> (:Subject {slug : \$subjectSlug})\n" +
                            "RETURN s,sp2",
                    mapOf("subjectSlug" to subjectSlug.value, "level" to proficencyLevel.name)
            ){ ProficencyRequirement(it["s"], it["sp2"]) }

    fun getResourcesForSubjectLevel(subjectSlug: Slug, proficencyLevel: ProficencyLevel): List<LearningResource> =
            neoDb.readAsList(
                    "MATCH (ls:LearningResource) -[:PROVIDES_PROFICENCY {level: \$level}]-> (s:Subject {slug: \$subjectSlug}) RETURN ls",
                    mapOf("subjectSlug" to subjectSlug.value, "level" to proficencyLevel.name)
            ){ LearningResource(it) }

    fun getRequirementContributingResource(resourceSlug: Slug): List<ProficencyRequirement> =
            neoDb.readListAndMap(
                    "MATCH (LearningResourece {slug : \$resourceSlug}) -[r:PROVIDES_PROFICENCY]-> (s:Subject) RETURN r,s",
                    mapOf("resourceSlug" to resourceSlug.value)
            ){ ProficencyRequirement(it["s"], it["r"]) }

    fun getAuthDataFor(user: UserName): UserAuthData? =
            neoDb.readAsEntity(
                    "MATCH (u:User {username: \$username}) RETURN u",
                    mapOf("username" to user.value)
            ) { UserAuthData(it) }
}

private fun Career(v: Value): Career = Career(EntityName(v.string("name")), Slug(v.string("slug")))
private fun Subject(v: Value): Subject = Subject(EntityName(v.string("name")), Slug(v.string("slug")))
private fun LearningResource(v: Value) = LearningResource(
        type = LearningResourceType.valueOf(v.string("type")),
        name = AnyName(v.string("name")),
        slug = Slug(v.string("slug")),
        url = Url(v.string("url")),
        language = LangISO639(v.string("language")),
        likes = PositiveQuantity(v.int("likes")),
        dislikes = PositiveQuantity(v.int("dislikes")),
        imageUrl = Url(v.string("imageUrl"))
)
private fun ProficencyRequirement(subjectValue: Value, proficencyValue: Value) = ProficencyRequirement(
        Subject(subjectValue),
        ProficencyLevel.valueOf(proficencyValue["level"].asString())
)
private fun UserAuthData(v: Value): UserAuthData? {
    if (v.string("username") == null) return null

    return UserAuthData(
            userName = UserName((v.string("username"))),
            passwordHash = SHA1Hash(v.string("pass"))
    )
}