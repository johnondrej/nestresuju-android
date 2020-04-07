package cz.nestresuju.model.entities.api.program.first

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity with results of first program.
 */
@JsonClass(generateAdapter = true)
class ApiProgramFirstResults(
    val target: String,
    val completion: String,
    val satisfiability: Int,
    val reason: String,
    val deadline: String,
    val summarizedTarget: String,
    val programCompletedDate: ZonedDateTime
)