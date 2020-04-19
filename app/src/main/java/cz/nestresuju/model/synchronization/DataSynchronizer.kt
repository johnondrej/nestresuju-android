package cz.nestresuju.model.synchronization

import cz.nestresuju.model.converters.*
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange
import cz.nestresuju.model.entities.domain.program.evaluation.ProgramEvaluation
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Class responsible for sending changes in local database to remote API.
 */
interface DataSynchronizer {

    suspend fun synchronizeAll()

    suspend fun synchronizeProgram()

    suspend fun synchronizeDiary()

    suspend fun addProgramEvaluationRequest(request: ProgramEvaluation)

    suspend fun addDiarySynchronizationRequest(request: DbSynchronizerDiaryChange)
}

class DataSynchronizerImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val programEvaluationConverter: ProgramEvaluationConverter,
    private val programFirstConverter: ProgramFirstConverter,
    private val programSecondConverter: ProgramSecondConverter,
    private val programThirdConverter: ProgramThirdConverter,
    private val programFourthConverter: ProgramFourthConverter
) : DataSynchronizer {

    override suspend fun synchronizeAll() {
        synchronizeProgram()
        synchronizeDiary()
    }

    override suspend fun synchronizeProgram() {
        try {
            val programFirstDao = database.programFirstDao()
            val programFirstResults = programFirstDao.getResults()

            if (!programFirstResults.synchronizedWithApi && programFirstResults.programCompleted != null) {
                // TODO: uncomment below when API is ready
                // apiDefinition.submitFirstProgramResults(programFirstConverter.dbProgramFirstResultsToApi(programFirstResults))
                programFirstDao.updateResults(programFirstResults.copy(synchronizedWithApi = true))
            }
        } catch (e: Exception) {
            // silent fail, synchronization will be performed next time
        }

        try {
            val programSecondDao = database.programSecondDao()
            val programSecondResults = programSecondDao.getResults()

            if (!programSecondResults.synchronizedWithApi && programSecondResults.programCompleted != null) {
                // TODO: uncomment below when API is ready
                // apiDefinition.submitSecondProgramResults(programSecondConverter.dbProgramSecondResultsToApi(programSecondResults))
                programSecondDao.updateResults(programSecondResults.copy(synchronizedWithApi = true))
            }
        } catch (e: Exception) {
            // silent fail, synchronization will be performed next time
        }

        try {
            val programThirdDao = database.programThirdDao()
            val programThirdResults = programThirdDao.getFullResults()

            if (!programThirdResults.results.synchronizedWithApi && programThirdResults.results.programCompleted != null) {
                // TODO: uncomment below when API is ready
                // apiDefinition.submitThirdProgramResults(programThirdConverter.dbProgramThirdResultsToApi(programThirdResults))
                programThirdDao.updateResults(programThirdResults.copy(results = programThirdResults.results.copy(synchronizedWithApi = true)))
            }
        } catch (e: Exception) {
            // silent fail, synchronization will be performed next time
        }

        try {
            val programFourthDao = database.programFourthDao()
            val programFourthResults = programFourthDao.getResults()

            if (!programFourthResults.results.synchronizedWithApi && programFourthResults.results.programCompleted != null) {
                // TODO: uncomment below when API is ready
                // apiDefinition.submitFourthProgramResults(programFourthConverter.dbProgramFourthToApi(programFourthResults))
                programFourthDao.updateResults(results = programFourthResults.results.copy(synchronizedWithApi = true))
            }
        } catch (e: Exception) {
            // silent fail, synchronization will be performed next time
        }
    }

    override suspend fun synchronizeDiary() {
        database.synchronizerDao().getAllDiaryChanges().forEach { diaryChange ->
            try {
                performDiarySynchronization(diaryChange)
            } catch (e: Exception) {
                // silent fail, synchronization will be performed next time
            }
        }
    }

    override suspend fun addProgramEvaluationRequest(request: ProgramEvaluation) {
        database.programEvaluationDao().addProgramEvaluation(programEvaluationConverter.programEvaluationToDb(request))

        GlobalScope.launch {
            // GlobalScope is used because we do not want to stop uploading entry to server when user goes to another screen
            performProgramEvaluationsSynchronization()
        }
    }

    override suspend fun addDiarySynchronizationRequest(request: DbSynchronizerDiaryChange) {
        val existingRequest = database.synchronizerDao().findDiaryChange(request.id)
        val resultRequest = if (existingRequest == null) {
            database.synchronizerDao().addDiaryChange(request)
            request
        } else {
            when (request.changeRequestType) {
                DbSynchronizerDiaryChange.CHANGE_EDIT -> {
                    existingRequest.copy(text = request.text!!).also { database.synchronizerDao().updateDiaryChange(it) }
                }
                DbSynchronizerDiaryChange.CHANGE_DELETE -> when (existingRequest.changeRequestType) {
                    DbSynchronizerDiaryChange.CHANGE_ADD -> {
                        database.synchronizerDao().deleteDiaryChange(diaryChangeId = existingRequest.id)
                        null
                    }
                    DbSynchronizerDiaryChange.CHANGE_EDIT -> DbSynchronizerDiaryChange(
                        id = existingRequest.id, changeRequestType = DbSynchronizerDiaryChange.CHANGE_DELETE
                    ).also { database.synchronizerDao().updateDiaryChange(it) }
                    else -> null
                }
                else -> null
            }
        }

        resultRequest?.let { diaryChange ->
            // GlobalScope is used because we do not want to stop uploading entry to server when user goes to another screen
            GlobalScope.launch {
                try {
                    performDiarySynchronization(diaryChange)
                } catch (e: Exception) {
                    // silent fail, synchronization will be performed next time
                }
            }
        }
    }

    private suspend fun performDiarySynchronization(request: DbSynchronizerDiaryChange) {
        when (request.changeRequestType) {
            DbSynchronizerDiaryChange.CHANGE_ADD -> {
                apiDefinition.createNewDiaryEntry(
                    ApiNewDiaryEntry(
                        entryType = request.entryType,
                        moodLevel = request.stressLevel,
                        questionId = request.questionId,
                        text = request.text!!
                    )
                )
            }
            DbSynchronizerDiaryChange.CHANGE_EDIT -> {
                apiDefinition.editDiaryEntry(
                    request.id, ApiNewDiaryEntry(
                        entryType = request.entryType,
                        text = request.text!!
                    )
                )
            }
            DbSynchronizerDiaryChange.CHANGE_DELETE -> {
                apiDefinition.deleteDiaryEntry(request.id)
            }
        }
        database.synchronizerDao().deleteDiaryChange(request.id)
    }

    private suspend fun performProgramEvaluationsSynchronization() {
        val evaluationDao = database.programEvaluationDao()
        val evaluations = evaluationDao.getAllEvaluations()

        evaluations.forEach { evaluation ->
            try {
                // TODO: uncomment below when API is ready
                // apiDefinition.submitProgramEvaluation(evaluation.programId, programEvaluationConverter.dbProgramEvaluationToApi(evaluation))
                // evaluationDao.deleteProgramEvaluation(evaluation.programId)
            } catch (e: Exception) {
                // silent fail, synchronization will be performed next time
            }
        }
    }
}