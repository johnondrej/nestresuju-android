package cz.nestresuju.model.entities.api.program.overview

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity with information about program state.
 */
@JsonClass(generateAdapter = true)
class ApiProgramOverview(
    val order: Int,
    val title: String,
    val name: String,
    val completed: Boolean,
    val evaluated: Boolean,
    val startDate: ZonedDateTime?
)