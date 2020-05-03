package cz.nestresuju.model.entities.api.deadline

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity with date of phase 1 research end.
 */
@JsonClass(generateAdapter = true)
class ProgramDeadlineResponse(
    val deadline: ZonedDateTime?
)