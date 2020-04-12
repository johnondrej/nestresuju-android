package cz.nestresuju.model.entities.database.diary

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Database class representing diary entry with full question details.
 */
data class DbDiaryEntryWithQuestion(
    @Embedded val diaryEntry: DbDiaryEntry,
    @Relation(
        parentColumn = "question_id",
        entityColumn = "id"
    )
    val stressQuestion: DbStressQuestion?
)