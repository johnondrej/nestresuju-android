package cz.nestresuju.networking

/**
 * Constants related to API & networking.
 */
object NetworkingConstants {

    const val API_BASE_URL = "https://api.nestresuju.cz/"
    const val API_AUTH_BASE_URL = "https://auth.nestresuju.cz/"

    const val AUTH_CLIENT_ID = "client.mobile"
    const val AUTH_CLIENT_SECRET = "mobile_secret"
    const val AUTH_GRANT_TYPE_PASSWORD = "password"
    const val AUTH_GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    const val AUTH_SCOPE = "nestresuju_mobile offline_access"
}