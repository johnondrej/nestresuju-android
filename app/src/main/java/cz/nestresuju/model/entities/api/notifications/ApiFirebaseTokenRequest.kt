package cz.nestresuju.model.entities.api.notifications

import com.squareup.moshi.JsonClass

/**
 * API request entity for adding/removing Firebase notifications tokens.
 */
@JsonClass(generateAdapter = true)
class ApiFirebaseTokenRequest(
    val firebaseToken: String
)