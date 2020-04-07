package cz.nestresuju.model.database.dao

import androidx.room.*
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults

/**
 * Database DAO for storing results of first program.
 */
@Dao
abstract class ProgramFirstDao {

    @Transaction
    open suspend fun getResults(): DbProgramFirstResults {
        if (getResultsCount() < 1) {
            updateResults(DbProgramFirstResults())
        }
        return getProgramResults()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateResults(results: DbProgramFirstResults)

    @Query("SELECT * FROM ProgramFirstResults")
    protected abstract suspend fun getProgramResults(): DbProgramFirstResults

    @Query("SELECT COUNT(id) FROM ProgramFirstResults")
    protected abstract suspend fun getResultsCount(): Int
}