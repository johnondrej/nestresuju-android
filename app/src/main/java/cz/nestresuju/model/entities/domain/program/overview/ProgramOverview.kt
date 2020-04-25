package cz.nestresuju.model.entities.domain.program.overview

import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

/**
 * Entity with information about program state.
 */
data class ProgramOverview(
    val id: String,
    val name: String,
    val completed: Boolean,
    val evaluated: Boolean,
    val startDate: ZonedDateTime?,
    val order: Int
) {

    val isOpened: Boolean = startDate?.toLocalDate()?.let { !LocalDate.now().isBefore(it) } ?: false
}