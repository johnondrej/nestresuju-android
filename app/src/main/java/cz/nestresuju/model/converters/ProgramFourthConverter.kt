package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.program.fourth.ApiProgramFourthQuestion
import cz.nestresuju.model.entities.api.program.fourth.ApiProgramFourthResults
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthQuestion
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthResults
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthResultsWithQuestions
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthQuestion
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthResults
import org.threeten.bp.ZonedDateTime

/**
 * Converter for entities related to program 4.
 */
interface ProgramFourthConverter {

    fun apiProgramFourthQuestionToDb(apiQuestion: ApiProgramFourthQuestion, order: Int): DbProgramFourthQuestion

    fun apiProgramFourthResultsToDb(apiResults: ApiProgramFourthResults): DbProgramFourthResultsWithQuestions

    fun dbProgramFourthToApi(dbResults: DbProgramFourthResultsWithQuestions): ApiProgramFourthResults

    fun dbProgramFourthResultsToDomain(dbResults: DbProgramFourthResultsWithQuestions): ProgramFourthResults

    fun programFourthResultsToDb(results: ProgramFourthResults): DbProgramFourthResultsWithQuestions
}

class ProgramFourthConverterImpl : ProgramFourthConverter {

    override fun apiProgramFourthQuestionToDb(apiQuestion: ApiProgramFourthQuestion, order: Int): DbProgramFourthQuestion {
        return DbProgramFourthQuestion(
            id = apiQuestion.id,
            order = order,
            type = apiQuestion.type,
            text = apiQuestion.text,
            answer = 0
        )
    }

    override fun apiProgramFourthResultsToDb(apiResults: ApiProgramFourthResults): DbProgramFourthResultsWithQuestions {
        return DbProgramFourthResultsWithQuestions(
            results = DbProgramFourthResults(
                stressEvent = apiResults.stressEvent,
                positives = apiResults.positives,
                presentScore = apiResults.scoreOfPresent,
                searchingScore = apiResults.scoreOfSearching,
                programCompleted = apiResults.programCompletedDate,
                progress = Int.MAX_VALUE,
                synchronizedWithApi = true
            ),
            questions = apiResults.results.map { apiQuestion ->
                DbProgramFourthQuestion(
                    id = apiQuestion.questionId,
                    order = 0,
                    type = apiQuestion.type,
                    text = "",
                    answer = apiQuestion.answer
                )
            }
        )
    }

    override fun dbProgramFourthToApi(dbResults: DbProgramFourthResultsWithQuestions): ApiProgramFourthResults {
        return ApiProgramFourthResults(
            stressEvent = dbResults.results.stressEvent,
            positives = dbResults.results.positives,
            scoreOfPresent = dbResults.results.presentScore,
            scoreOfSearching = dbResults.results.searchingScore,
            results = dbResults.questions.map { dbQuestion ->
                ApiProgramFourthResults.ApiProgramFourthAnswer(
                    questionId = dbQuestion.id,
                    type = dbQuestion.type,
                    answer = dbQuestion.answer
                )
            },
            programCompletedDate = dbResults.results.programCompleted ?: ZonedDateTime.now()
        )
    }

    override fun dbProgramFourthResultsToDomain(dbResults: DbProgramFourthResultsWithQuestions): ProgramFourthResults {
        return ProgramFourthResults(
            stressEvent = dbResults.results.stressEvent,
            positives = dbResults.results.positives,
            presentScore = dbResults.results.presentScore,
            searchingScore = dbResults.results.searchingScore,
            questions = dbResults.questions.map { dbQuestion ->
                ProgramFourthQuestion(
                    id = dbQuestion.id,
                    order = dbQuestion.order,
                    type = when (dbQuestion.type) {
                        1 -> ProgramFourthQuestion.QuestionType.PRESENT
                        2 -> ProgramFourthQuestion.QuestionType.SEARCHING
                        3 -> ProgramFourthQuestion.QuestionType.NOT_SCORED
                        else -> throw IllegalArgumentException("Invalid question type ${dbQuestion.type}")
                    }, text = dbQuestion.text,
                    answer = dbQuestion.answer
                )
            },
            programCompleted = dbResults.results.programCompleted,
            progress = dbResults.results.progress
        )
    }

    override fun programFourthResultsToDb(results: ProgramFourthResults): DbProgramFourthResultsWithQuestions {
        return DbProgramFourthResultsWithQuestions(
            results = DbProgramFourthResults(
                stressEvent = results.stressEvent,
                positives = results.positives,
                presentScore = results.presentScore,
                searchingScore = results.searchingScore,
                programCompleted = results.programCompleted,
                progress = results.progress,
                synchronizedWithApi = false
            ), questions = results.questions.map { question ->
                DbProgramFourthQuestion(
                    id = question.id,
                    order = question.order,
                    type = when (question.type) {
                        ProgramFourthQuestion.QuestionType.PRESENT -> 1
                        ProgramFourthQuestion.QuestionType.SEARCHING -> 2
                        ProgramFourthQuestion.QuestionType.NOT_SCORED -> 3
                    }, text = question.text,
                    answer = question.answer
                )
            }
        )
    }
}