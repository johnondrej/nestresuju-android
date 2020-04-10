package cz.nestresuju.model.entities.api.program.second

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity with results of second program.
 */
@JsonClass(generateAdapter = true)
class ApiProgramSecondResults(
    val relaxationDurationInSeconds: Long,
    val programCompletedDate: ZonedDateTime?
)