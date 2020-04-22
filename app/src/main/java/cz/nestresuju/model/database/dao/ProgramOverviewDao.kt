package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.nestresuju.model.entities.database.program.overview.DbProgramOverview
import kotlinx.coroutines.flow.Flow

/**
 * Database DAO for storing data about program states.
 */
@Dao
abstract class ProgramOverviewDao {

    @Query("SELECT * FROM ProgramStates")
    abstract fun observeProgramOverview(): Flow<List<DbProgramOverview>>

    @Transaction
    open suspend fun updateProgramOverview(programs: List<DbProgramOverview>) {
        deleteProgramOverview()
        addProgramOverview(programs)
    }

    @Transaction
    @Insert
    abstract suspend fun addProgramOverview(programs: List<DbProgramOverview>)

    @Query("UPDATE ProgramStates SET evaluated = 1 WHERE id = :programId")
    abstract suspend fun setProgramEvaluated(programId: String)

    @Query("DELETE FROM ProgramStates")
    abstract suspend fun deleteProgramOverview()
}