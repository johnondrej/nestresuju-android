package cz.nestresuju.model.database.dao

import androidx.room.*
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthQuestion
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthResults
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthResultsWithQuestions
import kotlinx.coroutines.flow.Flow

/**
 * Database DAO for storing results of fourth program.
 */
@Dao
abstract class ProgramFourthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateQuestions(questions: List<DbProgramFourthQuestion>)

    @Query("UPDATE ProgramFourthQuestions SET answer = :answer WHERE id = :questionId")
    abstract suspend fun updateQuestionAnswer(questionId: Long, answer: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateResults(results: DbProgramFourthResults)

    @Transaction
    @Query("SELECT * FROM ProgramFourthResults")
    abstract suspend fun getResults(): DbProgramFourthResultsWithQuestions

    @Query("SELECT * FROM ProgramFourthResults")
    abstract fun observeResults(): Flow<DbProgramFourthResultsWithQuestions>
}