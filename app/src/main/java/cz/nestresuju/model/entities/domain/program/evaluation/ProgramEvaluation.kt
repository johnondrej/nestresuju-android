package cz.nestresuju.model.entities.domain.program.evaluation

import org.threeten.bp.ZonedDateTime

/**
 * Entity representing program evaluation.
 */
data class ProgramEvaluation(
    val programId: String,
    val fulfillment: Int,
    val difficulty: Int,
    val message: String,
    val dateCreated: ZonedDateTime
)