package cz.nestresuju.model.entities.api.auth

import com.squareup.moshi.JsonClass

/**
 * API response with information about completion of necessary after-login steps.
 */
@JsonClass(generateAdapter = true)
class LoginChecklistResponse(
    val constentGiven: Boolean,
    val inputTestSubmitted: Boolean,
    val screeningTestSubmitted: Boolean
)