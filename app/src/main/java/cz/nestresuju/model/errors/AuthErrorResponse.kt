package cz.nestresuju.model.errors

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Error response returned from Auth API.
 */
@JsonClass(generateAdapter = true)
class AuthErrorResponse(
    @Json(name = "error") val error: String,
    @Json(name = "error_description") val errorDescription: String?
)