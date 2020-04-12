package cz.nestresuju.model.entities.database.program.third

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Database class for storing user's activities for program 3.
 */
@Entity(
    tableName = "ProgramThirdActivities",
    foreignKeys = [ForeignKey(
        entity = DbProgramThirdResults::class,
        parentColumns = ["id"],
        childColumns = ["results_id"]
    )]
)
class DbProgramThirdActivity(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "results_id") val resultsId: Long = 0,
    @ColumnInfo(name = "hours") val hours: Int,
    @ColumnInfo(name = "minutes") val minutes: Int,
    @ColumnInfo(name = "user_defined") val userDefined: Boolean
)