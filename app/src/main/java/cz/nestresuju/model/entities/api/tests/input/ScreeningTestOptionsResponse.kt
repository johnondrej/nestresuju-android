package cz.nestresuju.model.entities.api.tests.input

import com.squareup.moshi.JsonClass
import cz.nestresuju.model.entities.api.TimestampedResponse

/**
 * API response with all options for screening test.
 */
@JsonClass(generateAdapter = true)
class ScreeningTestOptionsResponse(
    override val timestamp: Long,
    val items: List<ApiScreeningTestOption>
) : TimestampedResponse {

    @JsonClass(generateAdapter = true)
    class ApiScreeningTestOption(
        val id: Long,
        val order: Int,
        val text: String
    )
}