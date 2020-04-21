package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.ProgramFourthConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.program.fourth.ApiProgramFourthQuestion
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthQuestion
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthResults
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.ZonedDateTime

/**
 * Repository for accessing data related to fourth program.
 */
interface ProgramFourthRepository : ProgramRepository<ProgramFourthResults> {

    suspend fun fetchQuestions()

    suspend fun updateQuestionAnswer(questionId: Long, answer: Int)
}

class ProgramFourthRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val dataSynchronizer: DataSynchronizer,
    private val entityConverter: ProgramFourthConverter
) : ProgramFourthRepository {

    override suspend fun fetchQuestions() {
        // TODO: remove mock data when API is ready
        val apiQuestions = List(15) { index ->
            val type = when {
                index + 1 > 10 -> 3
                (index + 1).rem(2) == 0 -> 1
                (index + 1).rem(2) == 1 -> 2
                else -> 3
            }

            ApiProgramFourthQuestion(
                id = index.toLong(),
                type = type,
                text = "Otázka dotazníku ${index + 1} (typ $type)"
            )
        }
        // val apiQuestions = apiDefinition.getFourthProgramQuestions(0).questions
        val questionsOrder = (1..(apiQuestions.size)).shuffled()

        database.programFourthDao().updateQuestions(apiQuestions.mapIndexed { index, apiQuestion ->
            entityConverter.apiProgramFourthQuestionToDb(apiQuestion, questionsOrder[index])
        })
    }

    override suspend fun updateQuestionAnswer(questionId: Long, answer: Int) {
        database.programFourthDao().updateQuestionAnswer(questionId, answer)
    }

    override suspend fun fetchProgramResults() {
        val apiResults = apiDefinition.getFourthProgramResults()
        database.programFourthDao().updateResults(entityConverter.apiProgramFourthResultsToDb(apiResults).results)
    }

    override suspend fun getProgramResults() = entityConverter.dbProgramFourthResultsToDomain(database.programFourthDao().getResults())

    override suspend fun observeProgramResults(): Flow<ProgramFourthResults> {
        return database.programFourthDao().observeResults()
            .map { dbResults -> entityConverter.dbProgramFourthResultsToDomain(dbResults) }
    }

    override suspend fun updateProgramResults(updater: (ProgramFourthResults) -> ProgramFourthResults) {
        database.programFourthDao().updateResults(entityConverter.programFourthResultsToDb(updater(getProgramResults())).results)
    }

    override suspend fun submitResults() {
        val programDao = database.programFourthDao()
        val results = entityConverter.dbProgramFourthResultsToDomain(programDao.getResults())
        val presentScore = results.questions.filter { it.type == ProgramFourthQuestion.QuestionType.PRESENT }.sumBy { it.answer!!.intValue }
        val searchingScore = results.questions.filter { it.type == ProgramFourthQuestion.QuestionType.SEARCHING }.sumBy { it.answer!!.intValue }

        programDao.updateResults(
            entityConverter.programFourthResultsToDb(
                results.copy(
                    presentScore = presentScore,
                    searchingScore = searchingScore,
                    programCompleted = ZonedDateTime.now()
                )
            ).results
        )
        dataSynchronizer.synchronizeProgram()
    }
}