package cz.nestresuju.model.entities.domain.program.fourth

import org.threeten.bp.ZonedDateTime

/**
 * Entity representing fourth program results.
 */
data class ProgramFourthResults(
    val stressEvent: String,
    val positives: String,
    val presentScore: Int,
    val searchingScore: Int,
    val questions: List<ProgramFourthQuestion>,
    val programCompleted: ZonedDateTime?,
    val progress: Int
)