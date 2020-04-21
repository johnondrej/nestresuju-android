package cz.nestresuju.networking

/**
 * Constants related to API & networking.
 */
object NetworkingConstants {

    const val API_BASE_URL = "https://stage.api.nestresuju.cz/"
    const val API_AUTH_BASE_URL = "https://stage.auth.nestresuju.cz/"

    const val AUTH_CLIENT_ID = "client.android"
    const val AUTH_CLIENT_SECRET = "android_secret"
    const val AUTH_GRANT_TYPE_PASSWORD = "password"
    const val AUTH_GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    const val AUTH_SCOPE = "nestresuju_mobile offline_access"
}