package cz.nestresuju.model.entities.api

import com.squareup.moshi.JsonClass

/**
 * API response with diary entries.
 */
@JsonClass(generateAdapter = true)
class DiaryEntriesResponse(
    override val timestamp: Long,
    val items: List<ApiDiaryEntry>
) : TimestampedResponse