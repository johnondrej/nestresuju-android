package cz.nestresuju.model.entities.domain.program.second

import org.threeten.bp.ZonedDateTime

/**
 * Entity representing second program results.
 */
data class ProgramSecondResults(
    val relaxationDuration: Long,
    val programCompleted: ZonedDateTime?,
    val progress: Int
)