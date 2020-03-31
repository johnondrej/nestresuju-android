package cz.nestresuju.model.synchronization

import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.database.diary.SynchronizerDbDiaryChange
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Class responsible for sending changes in local database to remote API.
 */
interface DataSynchronizer {

    suspend fun synchronizeAll()

    suspend fun synchronizeDiary()

    suspend fun addDiarySynchronizationRequest(request: SynchronizerDbDiaryChange)
}

class DataSynchronizerImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase
) : DataSynchronizer {

    override suspend fun synchronizeAll() {
        synchronizeDiary()
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

    override suspend fun addDiarySynchronizationRequest(request: SynchronizerDbDiaryChange) {
        val existingRequest = database.synchronizerDao().findDiaryChange(request.id)
        val resultRequest = if (existingRequest == null) {
            database.synchronizerDao().addDiaryChange(request)
            request
        } else {
            when (request.changeRequestType) {
                SynchronizerDbDiaryChange.CHANGE_EDIT -> {
                    existingRequest.copy(text = request.text!!).also { database.synchronizerDao().updateDiaryChange(it) }
                }
                SynchronizerDbDiaryChange.CHANGE_DELETE -> when (existingRequest.changeRequestType) {
                    SynchronizerDbDiaryChange.CHANGE_ADD -> {
                        database.synchronizerDao().deleteDiaryChange(diaryChangeId = existingRequest.id)
                        null
                    }
                    SynchronizerDbDiaryChange.CHANGE_EDIT -> SynchronizerDbDiaryChange(
                        id = existingRequest.id, changeRequestType = SynchronizerDbDiaryChange.CHANGE_DELETE
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

    private suspend fun performDiarySynchronization(request: SynchronizerDbDiaryChange) {
        when (request.changeRequestType) {
            SynchronizerDbDiaryChange.CHANGE_ADD -> {
                apiDefinition.createNewDiaryEntry(
                    ApiNewDiaryEntry(
                        entryType = request.entryType,
                        moodLevel = request.stressLevel,
                        questionId = request.questionId,
                        text = request.text!!
                    )
                )
            }
            SynchronizerDbDiaryChange.CHANGE_EDIT -> {
                apiDefinition.editDiaryEntry(
                    request.id, ApiNewDiaryEntry(
                        entryType = request.entryType,
                        text = request.text!!
                    )
                )
            }
            SynchronizerDbDiaryChange.CHANGE_DELETE -> {
                apiDefinition.deleteDiaryEntry(request.id)
            }
        }
        database.synchronizerDao().deleteDiaryChange(request.id)
    }
}