package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.DiaryEntitiesConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.model.entities.domain.diary.StressQuestion
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
    private val diaryEntitiesConverter: DiaryEntitiesConverter
) : DiaryRepository {

    private companion object {

        private const val ENTRY_TYPE_STRESS_LEVEL = 1
        private const val ENTRY_TYPE_NOTE = 2
    }

    override fun observeDiaryEntries() = database.diaryDao().observeEntries().map { entries ->
        entries.map { diaryEntitiesConverter.dbDiaryEntryToDomain(it) }
    }.distinctUntilChanged()

    override suspend fun fetchDiaryEntries() {
        val apiQuestions = apiDefinition.getDiaryMoodQuestions(0).items
        val apiEntries = apiDefinition.getDiaryEntires(0).items

        database.diaryDao().updateDiary(
            diaryEntries = apiEntries.map { diaryEntitiesConverter.apiDiaryEntryToDb(it) },
            stressQuestions = apiQuestions.map { diaryEntitiesConverter.apiStressQuestionToDb(it) }
        )
    }

    override suspend fun getStressQuestions(): List<StressQuestion> {
        return database.diaryDao().getStressQuestions().map { diaryEntitiesConverter.dbStressQuestionToDomain(it) }
    }

    override suspend fun createStressLevelEntry(stressLevel: StressLevel, question: StressQuestion, answer: String) {
        database.diaryDao().addEntry(
            DbDiaryEntry(
                entryType = ENTRY_TYPE_STRESS_LEVEL,
                moodLevel = diaryEntitiesConverter.stressLevelToInt(stressLevel),
                questionId = question.id,
                text = answer
            )
        )

        apiDefinition.createNewDiaryEntry(
            ApiNewDiaryEntry(
                entryType = ENTRY_TYPE_STRESS_LEVEL,
                moodLevel = diaryEntitiesConverter.stressLevelToInt(stressLevel),
                questionId = question.id,
                text = answer
            )
        )
    }

    override suspend fun createNoteEntry(text: String) {
        database.diaryDao().addEntry(
            DbDiaryEntry(
                entryType = ENTRY_TYPE_NOTE,
                text = text
            )
        )

        apiDefinition.createNewDiaryEntry(
            ApiNewDiaryEntry(
                entryType = ENTRY_TYPE_NOTE,
                text = text
            )
        )
    }

    override suspend fun editEntry(entryId: Long, modifiedText: String) {
        database.diaryDao().editEntry(entryId, modifiedText)
        apiDefinition.editDiaryEntry(entryId, ApiNewDiaryEntry(text = modifiedText))
    }

    override suspend fun deleteEntry(entryId: Long) {
        database.diaryDao().deleteEntry(entryId)
        apiDefinition.deleteDiaryEntry(entryId)
    }
}