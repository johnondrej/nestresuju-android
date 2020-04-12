package cz.nestresuju.model.database.dao

import androidx.room.*
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdActivity
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdHours
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResults
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResultsWithActivities
import kotlinx.coroutines.flow.Flow

/**
 * Database DAO for storing results of third program.
 */
@Dao
abstract class ProgramThirdDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateResults(results: DbProgramThirdResults)

    @Transaction
    open suspend fun updateResults(results: DbProgramThirdResultsWithActivities) {
        updateResults(results.results)
        updateTimetable(results.timetable)
        updateActivities(results.activities)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateTimetable(timetable: List<DbProgramThirdHours>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateActivities(activities: List<DbProgramThirdActivity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateActivity(activity: DbProgramThirdActivity)

    @Query("DELETE FROM ProgramThirdActivities WHERE name = :activityName")
    abstract suspend fun removeActivity(activityName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateTimetable(timetableEntry: DbProgramThirdHours)

    @Transaction
    @Query("SELECT * FROM ProgramThirdResults")
    abstract suspend fun getFullResults(): DbProgramThirdResultsWithActivities

    @Query("SELECT * FROM ProgramThirdResults")
    abstract fun observeResults(): Flow<DbProgramThirdResultsWithActivities>
}