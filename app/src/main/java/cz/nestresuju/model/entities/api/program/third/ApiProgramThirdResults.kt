package cz.nestresuju.model.entities.api.program.third

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity with results of third program.
 */
@JsonClass(generateAdapter = true)
class ApiProgramThirdResults(
    val timetable: List<ApiHourEntry>,
    val activities: List<ApiActivityEntry>,
    val programCompletedDate: ZonedDateTime
) {

    @JsonClass(generateAdapter = true)
    class ApiHourEntry(
        val name: String,
        val time: String?
    )

    @JsonClass(generateAdapter = true)
    class ApiActivityEntry(
        val name: String,
        val duration: String
    )
}