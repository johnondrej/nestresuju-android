package cz.nestresuju.model.entities.api.diary

import com.squareup.moshi.JsonClass
import cz.nestresuju.model.entities.api.TimestampedResponse

/**
 * API response with diary entries.
 */
@JsonClass(generateAdapter = true)
class DiaryEntriesResponse(
    override val timestamp: Long,
    val items: List<ApiDiaryEntry>
) : TimestampedResponse