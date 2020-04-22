package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.program.evaluation.ApiProgramEvaluation
import cz.nestresuju.model.entities.database.program.evaluation.DbProgramEvaluation
import cz.nestresuju.model.entities.domain.program.evaluation.ProgramEvaluation

/**
 * Converter for entities for program evaluation.
 */
interface ProgramEvaluationConverter {

    fun programEvaluationToDb(evaluation: ProgramEvaluation): DbProgramEvaluation

    fun dbProgramEvaluationToApi(dbEvaluation: DbProgramEvaluation): ApiProgramEvaluation
}

class ProgramEvaluationConverterImpl : ProgramEvaluationConverter {

    override fun programEvaluationToDb(evaluation: ProgramEvaluation): DbProgramEvaluation {
        return DbProgramEvaluation(
            programId = evaluation.programId,
            fulfillment = evaluation.fulfillment,
            difficulty = evaluation.difficulty,
            message = evaluation.message,
            dateCreated = evaluation.dateCreated
        )
    }

    override fun dbProgramEvaluationToApi(dbEvaluation: DbProgramEvaluation): ApiProgramEvaluation {
        return ApiProgramEvaluation(
            programTitle = dbEvaluation.programId,
            fulfillment = dbEvaluation.fulfillment,
            difficulty = dbEvaluation.difficulty,
            message = dbEvaluation.message,
            dateCreated = dbEvaluation.dateCreated
        )
    }
}