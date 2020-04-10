package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.program.second.ApiProgramSecondResults
import cz.nestresuju.model.entities.database.program.second.DbProgramSecondResults
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.screens.program.second.ProgramSecondConstants

/**
 * Converter for entities related to program 2.
 */
interface ProgramSecondConverter {

    fun apiProgramSecondResultsToDb(apiResults: ApiProgramSecondResults): DbProgramSecondResults

    fun dbProgramSecondResultsToApi(dbResults: DbProgramSecondResults): ApiProgramSecondResults

    fun dbProgramSecondResultsToDomain(dbResults: DbProgramSecondResults): ProgramSecondResults

    fun programSecondResultsToDb(results: ProgramSecondResults): DbProgramSecondResults
}

class ProgramSecondConverterImpl : ProgramSecondConverter {

    override fun apiProgramSecondResultsToDb(apiResults: ApiProgramSecondResults): DbProgramSecondResults {
        return DbProgramSecondResults(
            relaxationDuration = apiResults.relaxationDurationInSeconds,
            programCompleted = apiResults.programCompletedDate,
            progress = ProgramSecondConstants.PHASES,
            synchronizedWithApi = true
        )
    }

    override fun dbProgramSecondResultsToApi(dbResults: DbProgramSecondResults): ApiProgramSecondResults {
        return ApiProgramSecondResults(
            relaxationDurationInSeconds = dbResults.relaxationDuration,
            programCompletedDate = dbResults.programCompleted
        )
    }

    override fun dbProgramSecondResultsToDomain(dbResults: DbProgramSecondResults): ProgramSecondResults {
        return ProgramSecondResults(
            relaxationDuration = dbResults.relaxationDuration,
            programCompleted = dbResults.programCompleted,
            progress = dbResults.progress
        )
    }

    override fun programSecondResultsToDb(results: ProgramSecondResults): DbProgramSecondResults {
        return DbProgramSecondResults(
            relaxationDuration = results.relaxationDuration,
            programCompleted = results.programCompleted,
            progress = results.progress,
            synchronizedWithApi = false
        )
    }
}