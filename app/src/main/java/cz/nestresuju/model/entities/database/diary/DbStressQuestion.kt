package cz.nestresuju.model.entities.database.diary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database class representing stress level question.
 */
@Entity(tableName = "StressQuestions")
data class DbStressQuestion(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "stress_level") val stressLevel: Int,
    @ColumnInfo(name = "text") val text: String
)