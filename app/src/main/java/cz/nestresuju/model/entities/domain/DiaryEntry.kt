package cz.nestresuju.model.entities.domain

import org.threeten.bp.LocalDateTime

/**
 * Diary entry (either note or stress level entry).
 */
sealed class DiaryEntry {

    data class NoteEntry(
        val id: Long,
        val text: String,
        val dateCreated: LocalDateTime,
        val dateModified: LocalDateTime
    ) : DiaryEntry()

    data class StressLevelEntry(
        val id: Long,
        val stressLevel: StressLevel,
        val question: StressQuestion,
        val answer: String,
        val dateCreated: LocalDateTime,
        val dateModified: LocalDateTime
    ) : DiaryEntry()
}