package cz.nestresuju.model.entities.api.library

import com.squareup.moshi.JsonClass
import cz.nestresuju.model.entities.api.TimestampedResponse

/**
 * API response returned from library endpoint.
 */
@JsonClass(generateAdapter = true)
class LibraryResponse(
    override val timestamp: Long,
    val libraryIntroduction: String?,
    val sections: List<ApiLibrarySection>
) : TimestampedResponse