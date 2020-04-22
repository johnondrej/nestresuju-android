package cz.nestresuju.model.entities.api.library

import com.squareup.moshi.JsonClass

/**
 * API entity representing one section from the library.
 */
@JsonClass(generateAdapter = true)
class ApiLibrarySection(
    val id: Long,
    val name: String?,
    val text: String?,
    val sections: List<ApiLibrarySection>
)