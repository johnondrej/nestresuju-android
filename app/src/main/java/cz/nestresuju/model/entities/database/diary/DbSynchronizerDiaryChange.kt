package cz.nestresuju.model.entities.database.diary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Class representing special entity for synchronization of diary entry.
 */
@Entity(
    tableName = "SynchronizationDiaryChanges",
    foreignKeys = [ForeignKey(
        entity = DbStressQuestion::class,
        parentColumns = ["id"],
        childColumns = ["question_id"]
    )]
)
data class DbSynchronizerDiaryChange(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "change_request_type") val changeRequestType: Int,
    @ColumnInfo(name = "entry_type") val entryType: Int? = null,
    @ColumnInfo(name = "stress_level") val stressLevel: Int? = null,
    @ColumnInfo(name = "question_id") val questionId: Long? = null,
    @ColumnInfo(name = "text") val text: String? = null
) {

    companion object {

        const val CHANGE_ADD = 1
        const val CHANGE_EDIT = 2
        const val CHANGE_DELETE = 3
    }
}