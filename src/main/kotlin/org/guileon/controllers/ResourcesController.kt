package org.guileon.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.views.View
import org.guileon.backends.ChangesBackend
import org.guileon.data.LearningResource
import org.guileon.backends.ResourcesBackend
import org.guileon.data.ProficencyRequirement
import javax.inject.Inject

data class ResourceViewModel(
        val resource: LearningResource,
        val requirements: List<ProficencyRequirement>,
        val metadataString: String,
        val pendingChanges: Int
)

@Controller("/resources")
class ResourcesController @Inject constructor(
        private val resourcesBackend: ResourcesBackend,
        private val changesBackend: ChangesBackend
){

    @Get("{resourceSlug}")
    @View("resource")
    fun resource(@PathVariable resourceSlug: String): HttpResponse<ResourceViewModel> {
        val resource = resourcesBackend.getResource(resourceSlug);
        val requirements = resourcesBackend.getRequirementsForResource(resourceSlug)
        val pendingChanges = changesBackend.getPendingChangesForResource(resourceSlug)
        val metadata = resourcesBackend.getMetadataForResource(resourceSlug);
        return HttpResponse.ok(
                ResourceViewModel(resource, requirements, metadata.asUIString(), pendingChanges)
        )
    }
}