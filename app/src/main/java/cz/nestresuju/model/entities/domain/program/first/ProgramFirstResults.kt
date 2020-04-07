package cz.nestresuju.model.entities.domain.program.first

import org.threeten.bp.ZonedDateTime

/**
 * Entity representing first program results.
 */
data class ProgramFirstResults(
    val target: String,
    val completion: String,
    val satisfiability: Int,
    val reason: String,
    val deadline: String,
    val summarizedTarget: String,
    val programCompleted: ZonedDateTime?,
    val progress: Int
)