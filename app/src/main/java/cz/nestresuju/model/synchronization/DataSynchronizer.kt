package cz.nestresuju.model.synchronization

import cz.nestresuju.model.converters.ProgramFirstEntitiesConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange
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

    suspend fun addDiarySynchronizationRequest(request: DbSynchronizerDiaryChange)
}

class DataSynchronizerImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val programFirstEntitiesConverter: ProgramFirstEntitiesConverter
) : DataSynchronizer {

    override suspend fun synchronizeAll() {
        synchronizeProgram()
        synchronizeDiary()
    }

    override suspend fun synchronizeProgram() {
        val programFirstDao = database.programFirstDao()
        val programFirstResults = programFirstDao.getResults()

        try {
            if (!programFirstResults.synchronizedWithApi && programFirstResults.programCompleted != null) {
                // TODO: uncomment below when API is ready
                // apiDefinition.submitFirstProgramResults(programFirstEntitiesConverter.dbProgramFirstResultsToApi(programFirstResults))
                programFirstDao.updateResults(programFirstResults.copy(synchronizedWithApi = true))
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
}