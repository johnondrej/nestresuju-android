package cz.nestresuju.model.entities.api.about

import com.squareup.moshi.JsonClass

/**
 * API response with information about project research.
 */
@JsonClass(generateAdapter = true)
class ResearchResponse(
    val text: String?,
    val subsections: List<ApiResearchSubsection>
)