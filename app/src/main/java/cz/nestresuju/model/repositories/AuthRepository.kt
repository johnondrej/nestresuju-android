package cz.nestresuju.model.repositories

import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.networking.AuthApiDefinition

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
        private const val GRANT_TYPE = "password"
        private const val SCOPE = "nestresuju_mobile"
    }

    suspend fun login(username: String, password: String) {
        val authResponse = authApiDefinition.login(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = GRANT_TYPE,
            scope = SCOPE,
            username = username,
            password = password
        )
        oAuthManager.saveCredentials(authResponse)
    }
}