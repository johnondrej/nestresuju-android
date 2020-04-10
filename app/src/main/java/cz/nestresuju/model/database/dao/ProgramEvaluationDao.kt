package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.nestresuju.model.entities.database.program.evaluation.DbProgramEvaluation

/**
 * Database DAO for storing program evaluation requests.
 */
@Dao
abstract class ProgramEvaluationDao {

    @Insert
    abstract suspend fun addProgramEvaluation(evaluation: DbProgramEvaluation)

    @Query("DELETE FROM ProgramEvaluations WHERE programId = :programId")
    abstract suspend fun deleteProgramEvaluation(programId: String)

    @Query("SELECT * FROM ProgramEvaluations")
    abstract suspend fun getAllEvaluations(): List<DbProgramEvaluation>
}