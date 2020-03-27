package cz.nestresuju.model.entities.api.diary

import com.squareup.moshi.JsonClass

/**
 * API representing new diary entry request (note or stress level entry).
 */
@JsonClass(generateAdapter = true)
class ApiNewDiaryEntry(
    val entryType: Int? = null,
    val moodLevel: Int? = null,
    val questionId: Long? = null,
    val text: String
)