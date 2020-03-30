package cz.nestresuju.model.synchronization

import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.database.diary.SynchronizerDbDiaryChange
import cz.nestresuju.networking.ApiDefinition

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addDiarySynchronizationRequest(request: SynchronizerDbDiaryChange) {
        val existingRequest = database.synchronizerDao().findDiaryChange(request.id)
        val resultRequest = if (existingRequest == null) {
            database.synchronizerDao().addDiaryChange(request)
            request
        } else {
            when (request.changeRequestType) {
                SynchronizerDbDiaryChange.CHANGE_EDIT -> existingRequest.copy(text = request.text!!)
                SynchronizerDbDiaryChange.CHANGE_DELETE -> when (existingRequest.changeRequestType) {
                    SynchronizerDbDiaryChange.CHANGE_ADD -> {
                        database.synchronizerDao().removeDiaryChange(diaryChangeId = existingRequest.id)
                        null
                    }
                    SynchronizerDbDiaryChange.CHANGE_EDIT -> SynchronizerDbDiaryChange(
                        id = existingRequest.id, changeRequestType = SynchronizerDbDiaryChange.CHANGE_DELETE
                    )
                    else -> null
                }
                else -> null
            }
        }

        resultRequest?.let { performDiarySynchronization(it) }
    }

    private suspend fun performDiarySynchronization(request: SynchronizerDbDiaryChange) {
        try {
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
            database.synchronizerDao().removeDiaryChange(request.id)
        } catch (e: Exception) {
            // silent fail, synchronization will be performed next time
        }
    }
}