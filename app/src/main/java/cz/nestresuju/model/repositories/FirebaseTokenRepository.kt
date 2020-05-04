package cz.nestresuju.model.repositories

import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor
import cz.nestresuju.model.entities.api.notifications.ApiFirebaseTokenRequest
import cz.nestresuju.model.errors.ServerException
import cz.nestresuju.model.errors.constants.HttpErrorCodes
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Repository for handling Firebase notification tokens.
 */
interface FirebaseTokenRepository {

    fun onNewFirebaseToken(token: String)

    fun registerFirebaseToken() // after login, after new token

    fun unregisterFirebaseToken() // after logout

    fun unregisterInvalidFirebaseToken() // on app start
}

class FirebaseTokenRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val oAuthManager: OAuthManager,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : FirebaseTokenRepository {

    override fun onNewFirebaseToken(token: String) {
        sharedPreferencesInteractor.setFirebaseToken(token)

        if (oAuthManager.accessToken != null || oAuthManager.refreshToken != null) {
            registerFirebaseToken()
        }
    }

    override fun registerFirebaseToken() {
        GlobalScope.launch {
            sharedPreferencesInteractor.getFirebaseToken()?.let { token ->
                try {
                    apiDefinition.registerFirebaseToken(ApiFirebaseTokenRequest(token))
                } catch (e: Exception) {
                    // do nothing, token will be updated after it's obtained again
                }
            }
        }
    }

    override fun unregisterFirebaseToken() {
        GlobalScope.launch {
            sharedPreferencesInteractor.getFirebaseToken()?.let { token ->
                try {
                    apiDefinition.removeFirebaseToken(token)
                } catch (e: ServerException) {
                    if (e.httpCode == HttpErrorCodes.NOT_FOUND) {
                        // do nothing, token has already been unregistered
                    } else {
                        sharedPreferencesInteractor.setInvalidFirebaseToken(token)
                    }
                } catch (e: Exception) {
                    sharedPreferencesInteractor.setInvalidFirebaseToken(token)
                }
            }
        }
    }

    override fun unregisterInvalidFirebaseToken() {
        GlobalScope.launch {
            sharedPreferencesInteractor.getInvalidFirebaseToken()?.let { token ->
                try {
                    apiDefinition.removeFirebaseToken(token)
                    sharedPreferencesInteractor.clearInvalidFirebaseToken()
                } catch (e: ServerException) {
                    if (e.httpCode == HttpErrorCodes.NOT_FOUND) {
                        sharedPreferencesInteractor.clearInvalidFirebaseToken()
                    }
                } catch (e: Exception) {
                    // do nothing, token will be unregistered next time
                }
            }
        }
    }
}