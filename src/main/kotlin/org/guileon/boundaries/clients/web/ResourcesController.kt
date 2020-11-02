package org.guileon.boundaries.clients.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import org.guileon.boundaries.clients.web.providers.AuthViewDataProvider
import org.guileon.boundaries.clients.web.providers.AuthViewModel
import org.guileon.usecases.ChangesBackend
import org.guileon.domain.model.LearningResource
import org.guileon.usecases.ResourcesBackend
import org.guileon.domain.model.ProficencyRequirement
import org.javiermf.primitives.slug.Slug
import javax.inject.Inject

data class ResourceViewModel(
        val resource: LearningResource,
        val requirements: List<ProficencyRequirement>,
        val metadata: ResourceMetaDataView,
        val pendingChanges: Int
)

data class ResourceMetaDataView(
        val label: String,
        val imageUrl: String?
)

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/resources")
class ResourcesController @Inject constructor(
        private val resourcesBackend: ResourcesBackend,
        private val changesBackend: ChangesBackend,
        private val authViewDataProvider: AuthViewDataProvider
){

    @Get("{resourceSlug}")
    @View("resource")
    fun resource(@PathVariable resourceSlug: String): HttpResponse<AuthViewModel<ResourceViewModel>> {
        val slug = Slug(resourceSlug)

        val resource = resourcesBackend.getResource(slug) ?: return HttpResponse.serverError();
        val metadata = resourcesBackend.getMetadataForResource(slug, resource.type) ?: return HttpResponse.serverError();

        val requirements = resourcesBackend.getRequirementContributingResource(slug)
        val pendingChanges = changesBackend.getPendingChangesForResource(slug)

        val metadataView = ResourceMetaDataView(metadata.asUIString(), metadata.imageUrl()?.value)
        val view  = ResourceViewModel(resource, requirements, metadataView, pendingChanges)
        return HttpResponse.ok(authViewDataProvider.addAuthInfo(view))
    }
}