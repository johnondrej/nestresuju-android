package cz.nestresuju.model.entities.api.tests.input

import com.squareup.moshi.JsonClass

/**
 * Request entity for submitting input test results.
 */
@JsonClass(generateAdapter = true)
class ApiInputTestResults(val results: List<ApiInputTestQuestionAnswer>) {

    @JsonClass(generateAdapter = true)
    class ApiInputTestQuestionAnswer(
        val questionId: Long,
        val answer: Int
    )
}