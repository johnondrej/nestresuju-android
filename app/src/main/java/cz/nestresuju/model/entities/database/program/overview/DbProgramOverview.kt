package cz.nestresuju.model.entities.database.program.overview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

/**
 * Database entity with information about program state.
 */
@Entity(tableName = "ProgramStates")
data class DbProgramOverview(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "completed") val completed: Boolean,
    @ColumnInfo(name = "evaluated") val evaluated: Boolean,
    @ColumnInfo(name = "start_date") val startDate: ZonedDateTime?,
    @ColumnInfo(name = "order") val order: Int
)