package cz.nestresuju.model.errors

import com.squareup.moshi.JsonClass

/**
 * Error response returned from API.
 */
@JsonClass(generateAdapter = true)
class ErrorResponse(
    val errorCode: Int,
    val text: String
)