package cz.nestresuju.model.entities.api.diary

import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

/**
 * API entity for diary entry (note or stress level entry).
 */
@JsonClass(generateAdapter = true)
class ApiDiaryEntry(
    val id: Long,
    val entryType: Int,
    val moodLevel: Int?,
    val questionId: Long?,
    val text: String,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime
)