package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
import kotlinx.coroutines.flow.Flow

/**
 * Database DAO for storing results of first program.
 */
@Dao
abstract class ProgramFirstDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateResults(results: DbProgramFirstResults)

    @Query("SELECT * FROM ProgramFirstResults")
    abstract suspend fun getResults(): DbProgramFirstResults

    @Query("SELECT * FROM ProgramFirstResults")
    abstract fun observeResults(): Flow<DbProgramFirstResults>
}