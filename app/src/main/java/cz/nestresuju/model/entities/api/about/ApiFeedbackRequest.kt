package cz.nestresuju.model.entities.api.about

import com.squareup.moshi.JsonClass

/**
 * API entity with user feedback.
 */
@JsonClass(generateAdapter = true)
class ApiFeedbackRequest(
    val text: String
)