package cz.nestresuju.model.entities.api.diary

import com.squareup.moshi.JsonClass
import cz.nestresuju.model.entities.api.TimestampedResponse

/**
 * API response containing questions for specific mood levels.
 */
@JsonClass(generateAdapter = true)
class MoodQuestionsResponse(
    override val timestamp: Long,
    val items: List<ApiStressQuestion>
) : TimestampedResponse {

    @JsonClass(generateAdapter = true)
    class ApiStressQuestion(
        val id: Long,
        val moodLevel: Int,
        val text: String
    )
}