package cz.nestresuju.model.entities.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import cz.ackee.ackroutine.core.OAuthCredentials

/**
 * Entity returned from API on successful authentication.
 */
@JsonClass(generateAdapter = true)
class AuthResponse(
    @Json(name = "access_token") override val accessToken: String,
    @Json(name = "expires_in") override val expiresIn: Long,
    @Json(name = "refresh_token") override val refreshToken: String = "",
    @Json(name = "token_type") val tokenType: String,
    @Json(name = "scope") val scope: String
) : OAuthCredentials