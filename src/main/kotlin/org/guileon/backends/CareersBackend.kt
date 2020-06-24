package org.guileon.backends

import org.guileon.data.Career
import org.guileon.data.ProficencyLevel
import org.guileon.data.ProficencyRequirement
import org.guileon.data.Subject
import javax.inject.Singleton

@Singleton
class CareersBackend {
    fun getTopCareers() = listOf(
            Career("Backend Software Engineer", "back-sw-eng"),
            Career("Data Scientist", "data-scientist"),
            Career("Cloud Architect", "cloud-architect"),
            Career("UX Designer", "us-designer"),
            Career("Game Developer", "game-developer"),
            Career("DevOps Engineer", "devops-engineer"),
            Career("FullStack Developer", "fulstack-developer")
    )

    fun getCareer(careerSlug: String) = getTopCareers().first()

    fun getCareersRequirements(careerSlug: String) = listOf(
            ProficencyRequirement(
                    Subject("Software Development Practices", "sw-devel-pract"),
                    ProficencyLevel.master
            ),
            ProficencyRequirement(
                    Subject("Software Design Patterns", "sw-design-patterns"),
                    ProficencyLevel.master
            ),
            ProficencyRequirement(
                    Subject("Programming Languages", "programming-languages"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Software Architecture", "sw-arch"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Source Control Systems", "source-control"),
                    ProficencyLevel.expert
            ),
            ProficencyRequirement(
                    Subject("Networking", "networking"),
                    ProficencyLevel.apprentice
            )
    )
}