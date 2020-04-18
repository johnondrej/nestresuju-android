package cz.nestresuju.model.entities.api.program.fourth

import com.squareup.moshi.JsonClass
import cz.nestresuju.model.entities.api.TimestampedResponse

/**
 * API response containing questions to be shown in fourth program.
 */
@JsonClass(generateAdapter = true)
class ProgramFourthQuestionsResponse(
    override val timestamp: Long,
    val questions: List<ApiProgramFourthQuestion>
) : TimestampedResponse