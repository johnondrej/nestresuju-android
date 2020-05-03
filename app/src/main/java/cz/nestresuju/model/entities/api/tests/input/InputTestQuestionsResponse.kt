package cz.nestresuju.model.entities.api.tests.input

import com.squareup.moshi.JsonClass
import cz.nestresuju.model.entities.api.TimestampedResponse

/**
 * API response with all questions for input test.
 */
@JsonClass(generateAdapter = true)
class InputTestQuestionsResponse(
    override val timestamp: Long,
    val questions: List<ApiInputTestQuestion>
) : TimestampedResponse {

    @JsonClass(generateAdapter = true)
    class ApiInputTestQuestion(
        val id: Long,
        val order: Int,
        val text: String
    )
}