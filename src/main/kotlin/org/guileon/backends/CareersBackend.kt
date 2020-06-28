package org.guileon.backends

import org.guileon.data.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CareersBackend @Inject constructor(
        private val repository: TheRepository
){
    // TODO: Return only top careers
    fun getTopCareers() = repository.getAllCareers()
    fun getCareer(careerSlug: String) = repository.getCareer(careerSlug)
    fun getCareersRequirements(careerSlug: String) = repository.getCareersRequirements(careerSlug)
}