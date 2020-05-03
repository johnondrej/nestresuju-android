package cz.nestresuju.model.entities.api.about

import com.squareup.moshi.JsonClass

/**
 * API response with category of contacts.
 */
@JsonClass(generateAdapter = true)
class ApiContactsCategory(
    val name: String,
    val items: List<ApiContact>
)