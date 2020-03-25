package cz.nestresuju.model.repositories

import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.model.entities.api.AuthResponse
import cz.nestresuju.model.errors.ConsentNotGivenException
import cz.nestresuju.networking.AuthApiDefinition
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Repository providing access to user authentication.
 */
class AuthRepository(
    private val authApiDefinition: AuthApiDefinition,
    private val oAuthManager: OAuthManager
) {

    companion object {
        private const val CLIENT_ID = "client.mobile"
        private const val CLIENT_SECRET = "mobile_secret"
        private const val GRANT_TYPE_PASSWORD = "password"
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
        private const val SCOPE = "nestresuju_mobile offline_access"
    }

    private var consentContinuation: Continuation<Boolean>? = null

    suspend fun login(username: String, password: String, onShowConsent: () -> Unit) {
        val authResponse = authApiDefinition.login(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = GRANT_TYPE_PASSWORD,
            scope = SCOPE,
            username = username,
            password = password
        )

        if (isConsentConfirmed()) {
            onLoginCompleted(authResponse)
        } else {
            val confirmed = requestConsentConfirmation(onShowConsent)
            if (confirmed) {
                onLoginCompleted(authResponse)
            } else {
                logout()
                throw ConsentNotGivenException()
            }
        }
    }

    suspend fun loginWithRefreshToken(refreshToken: String): AuthResponse {
        val authResponse = authApiDefinition.loginWithRefreshToken(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = GRANT_TYPE_REFRESH_TOKEN,
            scope = SCOPE,
            refreshToken = refreshToken
        )

        onLoginCompleted(authResponse)
        return authResponse
    }

    fun onConsentConfirmed(confirmed: Boolean) {
        consentContinuation?.resume(confirmed)
    }

    private suspend fun requestConsentConfirmation(onShowConsent: () -> Unit) = suspendCoroutine<Boolean> {
        consentContinuation = it
        onShowConsent()
    }

    private fun onLoginCompleted(authResponse: AuthResponse) {
        oAuthManager.saveCredentials(authResponse)
    }

    private fun logout() {
        // TODO
    }

    private suspend fun isConsentConfirmed() = false // TODO: get real value from API when implemented
}