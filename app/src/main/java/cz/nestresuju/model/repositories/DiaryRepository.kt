package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.DiaryEntitiesConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.model.entities.domain.diary.StressQuestion
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Repository providing all data related to user's diary.
 */
interface DiaryRepository {

    fun observeDiaryEntries(): Flow<List<DiaryEntry>>

    suspend fun fetchDiaryEntries()

    suspend fun getStressQuestions(): List<StressQuestion>

    suspend fun createStressLevelEntry(stressLevel: StressLevel, question: StressQuestion, answer: String)

    suspend fun createNoteEntry(text: String)

    suspend fun editEntry(entryId: Long, modifiedText: String)

    suspend fun deleteEntry(entryId: Long)
}

class DiaryRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val dataSynchronizer: DataSynchronizer,
    private val entityConverter: DiaryEntitiesConverter
) : DiaryRepository {

    private companion object {

        private const val ENTRY_TYPE_STRESS_LEVEL = 1
        private const val ENTRY_TYPE_NOTE = 2
    }

    override fun observeDiaryEntries() = database.diaryDao().observeEntries().map { entries ->
        entries.map { entityConverter.dbDiaryEntryToDomain(it) }
    }.distinctUntilChanged()

    override suspend fun fetchDiaryEntries() {
        dataSynchronizer.synchronizeDiary()
        val apiQuestions = apiDefinition.getDiaryMoodQuestions(0).items
        val apiEntries = apiDefinition.getDiaryEntries(0).items

        database.diaryDao().updateDiary(
            diaryEntries = apiEntries.map { entityConverter.apiDiaryEntryToDb(it) },
            stressQuestions = apiQuestions.map { entityConverter.apiStressQuestionToDb(it) }
        )
    }

    override suspend fun getStressQuestions(): List<StressQuestion> {
        return database.diaryDao().getStressQuestions().map { entityConverter.dbStressQuestionToDomain(it) }
    }

    override suspend fun createStressLevelEntry(stressLevel: StressLevel, question: StressQuestion, answer: String) {
        val entryDbId = database.diaryDao().addEntry(
            DbDiaryEntry(
                entryType = ENTRY_TYPE_STRESS_LEVEL,
                moodLevel = entityConverter.stressLevelToInt(stressLevel),
                questionId = question.id,
                text = answer
            )
        )

        dataSynchronizer.addDiarySynchronizationRequest(
            DbSynchronizerDiaryChange(
                id = entryDbId,
                changeRequestType = DbSynchronizerDiaryChange.CHANGE_ADD,
                entryType = ENTRY_TYPE_STRESS_LEVEL,
                stressLevel = entityConverter.stressLevelToInt(stressLevel),
                questionId = question.id,
                text = answer
            )
        )
    }

    override suspend fun createNoteEntry(text: String) {
        val entryDbId = database.diaryDao().addEntry(
            DbDiaryEntry(
                entryType = ENTRY_TYPE_NOTE,
                text = text
            )
        )

        dataSynchronizer.addDiarySynchronizationRequest(
            DbSynchronizerDiaryChange(
                id = entryDbId,
                changeRequestType = DbSynchronizerDiaryChange.CHANGE_ADD,
                entryType = ENTRY_TYPE_NOTE,
                text = text
            )
        )
    }

    override suspend fun editEntry(entryId: Long, modifiedText: String) {
        database.diaryDao().editEntry(entryId, modifiedText)
        dataSynchronizer.addDiarySynchronizationRequest(
            DbSynchronizerDiaryChange(
                id = entryId,
                changeRequestType = DbSynchronizerDiaryChange.CHANGE_EDIT,
                text = modifiedText
            )
        )
    }

    override suspend fun deleteEntry(entryId: Long) {
        database.diaryDao().deleteEntry(entryId)
        dataSynchronizer.addDiarySynchronizationRequest(
            DbSynchronizerDiaryChange(
                id = entryId,
                changeRequestType = DbSynchronizerDiaryChange.CHANGE_DELETE
            )
        )
    }
}