package cz.nestresuju.model.repositories

import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.model.converters.AuthEntitiesConverter
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor
import cz.nestresuju.model.entities.api.auth.AuthResponse
import cz.nestresuju.model.entities.domain.auth.LoginChecklistCompletion
import cz.nestresuju.model.errors.ConsentNotGivenException
import cz.nestresuju.model.logouter.LogoutHandler
import cz.nestresuju.networking.ApiDefinition
import cz.nestresuju.networking.AuthApiDefinition
import cz.nestresuju.networking.NetworkingConstants.AUTH_CLIENT_ID
import cz.nestresuju.networking.NetworkingConstants.AUTH_CLIENT_SECRET
import cz.nestresuju.networking.NetworkingConstants.AUTH_GRANT_TYPE_PASSWORD
import cz.nestresuju.networking.NetworkingConstants.AUTH_GRANT_TYPE_REFRESH_TOKEN
import cz.nestresuju.networking.NetworkingConstants.AUTH_SCOPE
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
    private val oAuthManager: OAuthManager,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor,
    private val logoutHandler: LogoutHandler
) {

    private var consentContinuation: Continuation<Boolean>? = null

    suspend fun login(username: String, password: String, onShowConsent: () -> Unit): LoginChecklistCompletion {
        val authResponse = authApiDefinition.login(
            clientId = AUTH_CLIENT_ID,
            clientSecret = AUTH_CLIENT_SECRET,
            grantType = AUTH_GRANT_TYPE_PASSWORD,
            scope = AUTH_SCOPE,
            username = username,
            password = password
        )

        saveAuthCredentials(authResponse)

        val loginChecklist = updateLoginChecklistData()
        if (loginChecklist.consentGiven) {
            return loginChecklist
        } else {
            val confirmed = requestConsentConfirmation(onShowConsent)
            if (confirmed) {
                giveConsent()
                return loginChecklist.copy(consentGiven = true)
            } else {
                logout()
                throw ConsentNotGivenException()
            }
        }
    }

    suspend fun loginWithRefreshToken(refreshToken: String): AuthResponse {
        val authResponse = authApiDefinition.loginWithRefreshToken(
            clientId = AUTH_CLIENT_ID,
            clientSecret = AUTH_CLIENT_SECRET,
            grantType = AUTH_GRANT_TYPE_REFRESH_TOKEN,
            scope = AUTH_SCOPE,
            refreshToken = refreshToken
        )

        saveAuthCredentials(authResponse)
        return authResponse
    }

    fun onConsentConfirmed(confirmed: Boolean) {
        consentContinuation?.resume(confirmed)
    }

    suspend fun updateLoginChecklistData(): LoginChecklistCompletion {
        val loginChecklist = authEntitiesConverter.apiLoginChecklistToDomain(apiDefinition.getLoginPrerequirements())
        if (loginChecklist.consentGiven) {
            sharedPreferencesInteractor.setConsentGiven()
        }
        if (loginChecklist.inputTestSubmitted) {
            sharedPreferencesInteractor.setInputTestCompleted()
        }
        if (loginChecklist.screeningTestSubmitted) {
            sharedPreferencesInteractor.setScreeningTestCompleted()
        }
        if (loginChecklist.finalTestFirstSubmitted) {
            sharedPreferencesInteractor.setOutputTestFirstCompleted()
        }
        if (loginChecklist.finalTestSecondSubmitted) {
            sharedPreferencesInteractor.setOutputTestSecondCompleted()
        }
        return loginChecklist
    }

    private suspend fun giveConsent() {
        apiDefinition.giveUserConsent()
        sharedPreferencesInteractor.setConsentGiven()
    }

    private suspend fun requestConsentConfirmation(onShowConsent: () -> Unit) = suspendCoroutine<Boolean> {
        consentContinuation = it
        onShowConsent()
    }

    private fun saveAuthCredentials(authResponse: AuthResponse) {
        oAuthManager.saveCredentials(authResponse)
    }

    private fun logout() {
        logoutHandler.logout()
    }
}