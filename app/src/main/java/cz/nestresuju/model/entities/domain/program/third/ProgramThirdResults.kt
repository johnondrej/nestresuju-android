package cz.nestresuju.model.entities.domain.program.third

import org.threeten.bp.ZonedDateTime

/**
 * Entity representing third program results.
 */
data class ProgramThirdResults(
    val timetable: List<HourEntry>,
    val activities: List<ActivityEntry>,
    val target: String,
    val completion: String,
    val satisfiability: Int,
    val reason: String,
    val deadline: String,
    val summarizedTarget: String,
    val programCompleted: ZonedDateTime?,
    val progress: Int
) {

    data class HourEntry(
        val name: String,
        val hour: Int?,
        val minute: Int?
    )

    data class ActivityEntry(
        val name: String,
        val hours: Int,
        val minutes: Int,
        val userDefined: Boolean
    )
}