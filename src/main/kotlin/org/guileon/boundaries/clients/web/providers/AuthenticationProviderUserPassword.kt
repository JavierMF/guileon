package org.guileon.boundaries.clients.web.providers

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.guileon.domain.model.primitives.auth.ClearPassword
import org.guileon.domain.model.primitives.auth.UserName
import org.guileon.usecases.AuthBackend
import org.reactivestreams.Publisher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword @Inject constructor(
        private val authBackend: AuthBackend
) : AuthenticationProvider {
    override fun authenticate(httpRequest: HttpRequest<*>?, authenticationRequest: AuthenticationRequest<*, *>?): Publisher<AuthenticationResponse> {
        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse> ->
            if (areValidCredentials(authenticationRequest)) {
                val userDetails = UserDetails(authenticationRequest?.identity as String, ArrayList())
                emitter.onNext(userDetails)
                emitter.onComplete()
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }
        }, BackpressureStrategy.ERROR)
    }

    private fun areValidCredentials(request: AuthenticationRequest<*, *>?): Boolean =
        try {
            request != null &&
                    authBackend.isAuthValidForUser(
                            UserName(request.identity as String),
                            ClearPassword(request.secret as String)
                    )
        } catch (e: IllegalArgumentException) {
            false // TODO: Provider better feedback for this exception
        }
}