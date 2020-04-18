package cz.nestresuju.model.entities.api.program.fourth

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity with results of fourth program.
 */
@JsonClass(generateAdapter = true)
class ApiProgramFourthResults(
    val stressEvent: String,
    val positives: String,
    val scoreOfPresent: Int,
    val scoreOfSearching: Int,
    val results: List<ApiProgramFourthAnswer>,
    val programCompletedDate: ZonedDateTime
) {

    @JsonClass(generateAdapter = true)
    class ApiProgramFourthAnswer(
        val questionId: Long,
        val type: Int,
        val answer: Int
    )
}