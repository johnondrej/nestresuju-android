package cz.nestresuju.model.entities.api.diary

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * API entity for diary entry (note or stress level entry).
 */
@JsonClass(generateAdapter = true)
class ApiDiaryEntry(
    val id: Long,
    val entryType: Int,
    val moodLevel: Int?,
    @Json(name = "diaryMoodQuestionId") val questionId: Long?,
    val text: String,
    @Json(name = "created") val dateCreated: ZonedDateTime,
    @Json(name = "modified") val dateModified: ZonedDateTime?
) // TODO: remove @Json annotations and fake defaults when API sends data in correct format