package org.guileon.backends

import javax.inject.Singleton

@Singleton
class ChangesBackend {
    fun getPendingChangesForResource(resourceSlug: String) = 3
}