package cz.nestresuju.model.entities.database.program.third

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Database class representing program 3 results with list of belonging activities.
 */
data class DbProgramThirdResultsWithActivities(
    @Embedded val results: DbProgramThirdResults,
    @Relation(
        parentColumn = "id",
        entityColumn = "results_id"
    )
    val timetable: List<DbProgramThirdHours>,
    @Relation(
        parentColumn = "id",
        entityColumn = "results_id"
    )
    val activities: List<DbProgramThirdActivity>
)