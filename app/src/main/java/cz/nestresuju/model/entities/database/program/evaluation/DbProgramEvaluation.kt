package cz.nestresuju.model.entities.database.program.evaluation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database class for saved program evaluation.
 */
@Entity(tableName = "ProgramEvaluations")
data class DbProgramEvaluation(
    @PrimaryKey val programId: String,
    @ColumnInfo(name = "fulfillment") val fulfillment: Int,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "date_created") val dateCreated: ZonedDateTime
)