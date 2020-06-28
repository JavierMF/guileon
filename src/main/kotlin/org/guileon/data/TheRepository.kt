package org.guileon.data

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

    fun getCareer(careerSlug: String): Career?  =
            neoDb.readAsEntity(
                    "MATCH (c:Career {slug: \$careerSlug}) RETURN c",
                    mapOf("careerSlug" to careerSlug)
            ) { Career(it) }

    fun getCareersRequirements(careerSlug: String): List<ProficencyRequirement> =
        neoDb.readListAndMap(
                "MATCH (Career {slug : \$careerSlug}) -[:REQUIRES_PROFICENCY]-> (sp:SubjectProficency) -[:FOR_SUBJECT]-> (s:Subject) RETURN s,sp",
                mapOf("careerSlug" to careerSlug)
        ){ ProficencyRequirement(it["s"], it["sp"]) }

    fun getResource(resourceSlug: String): LearningResource? =
            neoDb.readAsEntity(
                    "MATCH (c:LearningResource {slug: \$resourceSlug}) RETURN c",
                    mapOf("resourceSlug" to resourceSlug)
            ) { LearningResource(it) }

    fun getResourceMetadata(resourceSlug: String): Map<String, Any> =
            neoDb.readAsEntity(
                    "MATCH (LearningResource {slug : \$resourceSlug}) - [tr:METADATA]-> (m:Metadata) RETURN m",
                    mapOf("resourceSlug" to resourceSlug)
            ) {
                it.asMap()
            } ?: emptyMap()

    fun getSubject(subjectSlug: String): Subject? =
        neoDb.readAsEntity(
                "MATCH (c:Subject {slug: \$subjectSlug}) RETURN c",
                mapOf("subjectSlug" to subjectSlug)
        ) { Subject(it) }

    fun getRequirementsForSubjectLevel(subjectSlug: String,  proficencyLevel: ProficencyLevel): List<ProficencyRequirement> =
            neoDb.readListAndMap(
                    "MATCH  (sp:SubjectProficency) -[:REQUIRES_PROFICENCY]-> (sp2:SubjectProficency) -[:FOR_SUBJECT]-> (s:Subject)\n" +
                            "WHERE (sp:SubjectProficency {level: \$level}) -[:FOR_SUBJECT]-> (:Subject {slug : \$subjectSlug})\n" +
                            "RETURN s,sp2",
                    mapOf("subjectSlug" to subjectSlug, "level" to proficencyLevel.name)
            ){ ProficencyRequirement(it["s"], it["sp2"]) }

    fun getResourcesForSubjectLevel(subjectSlug: String, proficencyLevel: ProficencyLevel): List<LearningResource> =
            neoDb.readAsList(
                    "MATCH (ls:LearningResource) -[:PROVIDES_PROFICENCY {level: \$level}]-> (s:Subject {slug: \$subjectSlug}) RETURN ls",
                    mapOf("subjectSlug" to subjectSlug, "level" to proficencyLevel.name)
            ){ LearningResource(it) }

    fun getRequirementContributingResource(resourceSlug: String): List<ProficencyRequirement> =
            neoDb.readListAndMap(
                    "MATCH (LearningResourece {slug : \$resourceSlug}) -[r:PROVIDES_PROFICENCY]-> (s:Subject) RETURN r,s",
                    mapOf("resourceSlug" to resourceSlug)
            ){ ProficencyRequirement(it["s"], it["r"]) }
}

private fun Career(v: Value):Career = Career(v.string("name"), v.string("slug"))
private fun Subject(v: Value):Subject = Subject(v.string("name"), v.string("slug"))
private fun LearningResource(v: Value) = LearningResource(
        type = LearningResourceType.valueOf(v.string("type")),
        name = v.string("name"),
        slug = v.string("slug"),
        url = v.string("url"),
        language = v.string("language"),
        likes = v.int("likes"),
        dislikes = v.int("dislikes"),
        imageUrl = v.string("imageUrl")
)
private fun ProficencyRequirement(subjectValue: Value, proficencyValue: Value) = ProficencyRequirement(
        Subject(subjectValue),
        ProficencyLevel.valueOf(proficencyValue["level"].asString())
)
