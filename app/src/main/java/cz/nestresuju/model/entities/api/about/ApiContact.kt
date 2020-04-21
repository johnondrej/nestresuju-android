package cz.nestresuju.model.entities.api.about

import com.squareup.moshi.JsonClass

/**
 * API entity with contact info related to one person.
 */
@JsonClass(generateAdapter = true)
class ApiContact(
    val image: String?,
    val heading: String,
    val description: String?,
    val email: String?,
    val phone: String?
)