package cz.nestresuju.model.entities.database.program.fourth

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database class representing results of fourth program.
 */
data class DbProgramFourthResults(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "stress_event") val stressEvent: String = "",
    @ColumnInfo(name = "positives") val positives: String = "",
    @ColumnInfo(name = "present_score") val presentScore: Int = 0,
    @ColumnInfo(name = "searching_score") val searchingScore: Int = 0,
    @ColumnInfo(name = "program_completed") val programCompleted: ZonedDateTime? = null,
    @ColumnInfo(name = "progress") val progress: Int = 0,
    @ColumnInfo(name = "synchronized") val synchronizedWithApi: Boolean = false
)