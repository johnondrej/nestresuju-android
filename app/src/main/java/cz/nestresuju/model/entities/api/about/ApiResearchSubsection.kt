package cz.nestresuju.model.entities.api.about

import com.squareup.moshi.JsonClass

/**
 * API entity representing one subsection with info about project research.
 */
@JsonClass(generateAdapter = true)
class ApiResearchSubsection(
    val name: String,
    val text: String
)