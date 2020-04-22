package cz.nestresuju.model.entities.api.program.overview

import com.squareup.moshi.JsonClass

/**
 * API response containing data about program states.
 */
@JsonClass(generateAdapter = true)
class ProgramOverviewResponse(
    val items: List<ApiProgramOverview>
)