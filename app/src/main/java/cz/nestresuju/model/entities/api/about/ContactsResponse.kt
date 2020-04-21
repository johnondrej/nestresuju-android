package cz.nestresuju.model.entities.api.about

import com.squareup.moshi.JsonClass

/**
 * API response with contact info.
 */
@JsonClass(generateAdapter = true)
class ContactsResponse(
    val categories: List<ApiContactsCategory>
)