package cz.nestresuju.model.entities.api.program.evaluation

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity for sending program evaluation.
 */
@JsonClass(generateAdapter = true)
class ApiProgramEvaluation(
    val fulfillment: Int,
    val difficulty: Int,
    val message: String,
    val dateCreated: ZonedDateTime
)