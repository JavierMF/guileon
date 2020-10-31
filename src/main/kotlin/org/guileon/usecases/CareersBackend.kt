package org.guileon.usecases

import org.guileon.boundaries.backends.persistence.TheRepository
import org.javiermf.primitives.slug.Slug
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CareersBackend @Inject constructor(
        private val repository: TheRepository
){
    // TODO: Return only top careers
    fun getTopCareers() = repository.getAllCareers()
    fun getCareer(careerSlug: Slug) = repository.getCareer(careerSlug)
    fun getCareersRequirements(careerSlug: Slug) = repository.getCareersRequirements(careerSlug)
}