package cz.nestresuju.model.entities.database.diary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database class representing entry in diary.
 */
@Entity(
    tableName = "DiaryEntries",
    foreignKeys = [ForeignKey(
        entity = DbStressQuestion::class,
        parentColumns = ["id"],
        childColumns = ["question_id"]
    )]
)
data class DbDiaryEntry(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "entry_type") val entryType: Int,
    @ColumnInfo(name = "mood_level") val moodLevel: Int?,
    @ColumnInfo(name = "question_id") val questionId: Long?,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "date_created") val dateCreated: ZonedDateTime,
    @ColumnInfo(name = "date_modified") val dateModified: ZonedDateTime
)