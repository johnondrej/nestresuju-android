package cz.nestresuju.model.repositories

import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.model.converters.AuthEntitiesConverter
import cz.nestresuju.model.entities.api.auth.AuthResponse
import cz.nestresuju.model.entities.api.auth.LoginChecklistResponse
import cz.nestresuju.model.entities.domain.auth.LoginChecklistCompletion
import cz.nestresuju.model.errors.ConsentNotGivenException
import cz.nestresuju.networking.ApiDefinition
import cz.nestresuju.networking.AuthApiDefinition
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Repository providing access to user authentication.
 */
class AuthRepository(
    private val authApiDefinition: AuthApiDefinition,
    private val apiDefinition: ApiDefinition,
    private val authEntitiesConverter: AuthEntitiesConverter,
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

    suspend fun login(username: String, password: String, onShowConsent: () -> Unit): LoginChecklistCompletion {
        val authResponse = authApiDefinition.login(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = GRANT_TYPE_PASSWORD,
            scope = SCOPE,
            username = username,
            password = password
        )

        val apiLoginChecklist = LoginChecklistResponse(
            constentGiven = false,
            inputTestSubmitted = false,
            screeningTestSubmitted = false
        ) // TODO: pass real value from API and inline this variable when API sends the data
        val loginChecklist = authEntitiesConverter.apiLoginChecklistToDomain(apiLoginChecklist)
        if (loginChecklist.consentGiven) {
            onLoginCompleted(authResponse)
            return loginChecklist
        } else {
            val confirmed = requestConsentConfirmation(onShowConsent)
            if (confirmed) {
                giveConsent()
                onLoginCompleted(authResponse)
                return loginChecklist.copy(consentGiven = true)
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

    private suspend fun giveConsent() {
        // TODO: connect to API when ready
        // apiDefinition.giveUserConsent()
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
}