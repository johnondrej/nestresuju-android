package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.nestresuju.model.entities.database.program.second.DbProgramSecondResults
import kotlinx.coroutines.flow.Flow

/**
 * Database DAO for storing results of second program.
 */
@Dao
abstract class ProgramSecondDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateResults(results: DbProgramSecondResults)

    @Query("SELECT * FROM ProgramSecondResults")
    abstract suspend fun getResults(): DbProgramSecondResults

    @Query("SELECT * FROM ProgramSecondResults")
    abstract fun observeResults(): Flow<DbProgramSecondResults>
}