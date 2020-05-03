package cz.nestresuju.model.entities.api.tests.input

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * Request entity for submitting input test results.
 */
@JsonClass(generateAdapter = true)
class ApiInputTestResults(
    val results: List<ApiInputTestQuestionAnswer>,
    val programCompletedDate: ZonedDateTime
) {

    @JsonClass(generateAdapter = true)
    class ApiInputTestQuestionAnswer(
        val questionId: Long,
        val answer: Int
    )
}