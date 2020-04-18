package cz.nestresuju.model.entities.database.program.fourth

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Database class representing program 4 results with list of belonging questions.
 */
data class DbProgramFourthResultsWithQuestions(
    @Embedded val results: DbProgramFourthResults,
    @Relation(
        parentColumn = "id",
        entityColumn = "results_id"
    )
    val questions: List<DbProgramFourthQuestion>
)