package cz.nestresuju.model.entities.database.program.second

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database class representing results of second program.
 */
@Entity(tableName = "ProgramSecondResults")
data class DbProgramSecondResults(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "duration") val relaxationDuration: Long = -1,
    @ColumnInfo(name = "programCompleted") val programCompleted: ZonedDateTime? = null,
    @ColumnInfo(name = "progress") val progress: Int = 0,
    @ColumnInfo(name = "synchronized") val synchronizedWithApi: Boolean = false
)