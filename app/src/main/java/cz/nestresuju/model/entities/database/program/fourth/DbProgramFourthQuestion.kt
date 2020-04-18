package cz.nestresuju.model.entities.database.program.fourth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Database class representing question to be shown in fourth program.
 */
@Entity(
    tableName = "ProgramFourthQuestions",
    foreignKeys = [ForeignKey(
        entity = DbProgramFourthResults::class,
        parentColumns = ["id"],
        childColumns = ["results_id"]
    )]
)
class DbProgramFourthQuestion(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "results_id") val resultsId: Long = 0,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "answer") val answer: Int
)