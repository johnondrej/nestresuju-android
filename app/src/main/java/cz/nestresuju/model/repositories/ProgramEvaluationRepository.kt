package cz.nestresuju.model.repositories

import cz.nestresuju.model.entities.domain.program.evaluation.ProgramEvaluation
import cz.nestresuju.model.synchronization.DataSynchronizer

/**
 * Repository for sending program evaluation.
 */
interface ProgramEvaluationRepository {

    suspend fun submitProgramEvaluation(programEvaluation: ProgramEvaluation)
}

class ProgramEvaluationRepositoryImpl(
    private val dataSynchronizer: DataSynchronizer
) : ProgramEvaluationRepository {

    override suspend fun submitProgramEvaluation(programEvaluation: ProgramEvaluation) {
        dataSynchronizer.addProgramEvaluationRequest(programEvaluation)
    }
}