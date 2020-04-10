package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.program.first.ApiProgramFirstResults
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import org.threeten.bp.ZonedDateTime

/**
 * Converter for entities related to program 1.
 */
interface ProgramFirstConverter {

    fun apiProgramFirstResultsToDb(apiResults: ApiProgramFirstResults): DbProgramFirstResults

    fun dbProgramFirstResultsToApi(dbResults: DbProgramFirstResults): ApiProgramFirstResults

    fun dbProgramFirstResultsToDomain(dbResults: DbProgramFirstResults): ProgramFirstResults

    fun programFirstResultsToDb(results: ProgramFirstResults): DbProgramFirstResults
}

class ProgramFirstConverterImpl : ProgramFirstConverter {

    override fun apiProgramFirstResultsToDb(apiResults: ApiProgramFirstResults): DbProgramFirstResults {
        return DbProgramFirstResults(
            target = apiResults.target,
            completion = apiResults.completion,
            satisfiability = apiResults.satisfiability,
            reason = apiResults.reason,
            deadline = apiResults.deadline,
            summarizedTarget = apiResults.summarizedTarget,
            programCompleted = apiResults.programCompletedDate,
            progress = 6,
            synchronizedWithApi = true
        )
    }

    override fun dbProgramFirstResultsToApi(dbResults: DbProgramFirstResults): ApiProgramFirstResults {
        return ApiProgramFirstResults(
            target = dbResults.target,
            completion = dbResults.completion,
            satisfiability = dbResults.satisfiability,
            reason = dbResults.reason,
            deadline = dbResults.deadline,
            summarizedTarget = dbResults.summarizedTarget,
            programCompletedDate = ZonedDateTime.now()
        )
    }

    override fun dbProgramFirstResultsToDomain(dbResults: DbProgramFirstResults): ProgramFirstResults {
        return ProgramFirstResults(
            target = dbResults.target,
            completion = dbResults.completion,
            satisfiability = dbResults.satisfiability,
            reason = dbResults.reason,
            deadline = dbResults.deadline,
            summarizedTarget = dbResults.summarizedTarget,
            programCompleted = dbResults.programCompleted,
            progress = dbResults.progress
        )
    }

    override fun programFirstResultsToDb(results: ProgramFirstResults): DbProgramFirstResults {
        return DbProgramFirstResults(
            target = results.target,
            completion = results.completion,
            satisfiability = results.satisfiability,
            reason = results.reason,
            deadline = results.deadline,
            summarizedTarget = results.summarizedTarget,
            programCompleted = results.programCompleted,
            progress = results.progress,
            synchronizedWithApi = false
        )
    }
}