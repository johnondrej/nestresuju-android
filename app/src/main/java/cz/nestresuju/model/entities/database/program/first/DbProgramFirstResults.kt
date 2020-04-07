package cz.nestresuju.model.entities.database.program.first

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database class representing results of first program.
 */
@Entity(tableName = "ProgramFirstResults")
data class DbProgramFirstResults(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "target") val target: String = "",
    @ColumnInfo(name = "completion") val completion: String = "",
    @ColumnInfo(name = "satisfiability") val satisfiability: Int = 0,
    @ColumnInfo(name = "reason") val reason: String = "",
    @ColumnInfo(name = "deadline") val deadline: String = "",
    @ColumnInfo(name = "summarizedTarget") val summarizedTarget: String = "",
    @ColumnInfo(name = "programCompleted") val programCompleted: ZonedDateTime? = null,
    @ColumnInfo(name = "progress") val progress: Int = 1
)