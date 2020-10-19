package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.views.View
import org.guileon.usecases.ChangesBackend
import org.guileon.domain.model.LearningResource
import org.guileon.usecases.ResourcesBackend
import org.guileon.domain.model.ProficencyRequirement
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
        val resource = resourcesBackend.getResource(resourceSlug) ?: return HttpResponse.serverError();
        val metadata = resourcesBackend.getMetadataForResource(resourceSlug, resource.type) ?: return HttpResponse.serverError();

        val requirements = resourcesBackend.getRequirementContributingResource(resourceSlug)
        val pendingChanges = changesBackend.getPendingChangesForResource(resourceSlug)
        return HttpResponse.ok(
                ResourceViewModel(resource, requirements, metadata.asUIString(), pendingChanges)
        )
    }
}