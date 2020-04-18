package cz.nestresuju.model.entities.database.program.third

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database class representing results of fourth program.
 */
@Entity(tableName = "ProgramThirdResults")
data class DbProgramThirdResults(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "target") val target: String = "",
    @ColumnInfo(name = "completion") val completion: String = "",
    @ColumnInfo(name = "satisfiability") val satisfiability: Int = 0,
    @ColumnInfo(name = "reason") val reason: String = "",
    @ColumnInfo(name = "deadline") val deadline: String = "",
    @ColumnInfo(name = "summarized_target") val summarizedTarget: String = "",
    @ColumnInfo(name = "program_completed") val programCompleted: ZonedDateTime? = null,
    @ColumnInfo(name = "progress") val progress: Int = 0,
    @ColumnInfo(name = "synchronized") val synchronizedWithApi: Boolean = false
)