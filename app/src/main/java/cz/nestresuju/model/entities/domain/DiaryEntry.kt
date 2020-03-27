package cz.nestresuju.model.entities.domain

import org.threeten.bp.LocalDateTime

/**
 * Diary entry (either note or stress level entry).
 */
sealed class DiaryEntry {

    abstract val id: Long
    abstract val text: String
    abstract val dateCreated: LocalDateTime

    data class NoteEntry(
        override val id: Long,
        override val text: String,
        override val dateCreated: LocalDateTime,
        val dateModified: LocalDateTime
    ) : DiaryEntry()

    data class StressLevelEntry(
        override val id: Long,
        val stressLevel: StressLevel,
        val question: StressQuestion,
        val answer: String,
        override val dateCreated: LocalDateTime,
        val dateModified: LocalDateTime
    ) : DiaryEntry() {

        override val text: String
            get() = answer
    }
}