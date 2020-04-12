package cz.nestresuju.model.entities.database.program.third

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Database class for storing user's timetable for program 3.
 */
@Entity(
    tableName = "ProgramThirdHours",
    foreignKeys = [ForeignKey(
        entity = DbProgramThirdResults::class,
        parentColumns = ["id"],
        childColumns = ["results_id"]
    )]
)
class DbProgramThirdHours(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "results_id") val resultsId: Long = 0,
    @ColumnInfo(name = "hour") val hour: Int?,
    @ColumnInfo(name = "minute") val minute: Int?
)