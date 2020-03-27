package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.DiaryEntitiesConverter
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressLevel
import cz.nestresuju.model.entities.domain.StressQuestion
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository providing all data related to user's diary.
 */
interface DiaryRepository {

    suspend fun fetchMoodQuestions(): List<StressQuestion>

    suspend fun fetchDiaryEntries(moodQuestions: List<StressQuestion>): List<DiaryEntry>

    suspend fun createStressLevelEntry(stressLevel: StressLevel, question: StressQuestion, answer: String)

    suspend fun createNoteEntry(text: String)

    suspend fun editEntry(entryId: Long, modifiedText: String)

    suspend fun deleteEntry(entry: DiaryEntry)
}

class DiaryRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val diaryEntitiesConverter: DiaryEntitiesConverter
) : DiaryRepository {

    private companion object {

        private const val ENTRY_TYPE_STRESS_LEVEL = 1
        private const val ENTRY_TYPE_NOTE = 2
    }

    override suspend fun fetchMoodQuestions(): List<StressQuestion> {
        return apiDefinition.getDiaryMoodQuestions(0).items.map { diaryEntitiesConverter.apiMoodQuestionToDomain(it) }
    }

    override suspend fun fetchDiaryEntries(moodQuestions: List<StressQuestion>): List<DiaryEntry> {
        return apiDefinition.getDiaryEntires(0).items.map {
            diaryEntitiesConverter.apiDiaryEntryToDomain(it, moodQuestions)
        }
    }

    override suspend fun createStressLevelEntry(stressLevel: StressLevel, question: StressQuestion, answer: String) {
        return apiDefinition.createNewDiaryEntry(
            ApiNewDiaryEntry(
                entryType = ENTRY_TYPE_STRESS_LEVEL,
                moodLevel = diaryEntitiesConverter.stressLevelToInt(stressLevel),
                questionId = question.id,
                text = answer
            )
        )
    }

    override suspend fun createNoteEntry(text: String) {
        return apiDefinition.createNewDiaryEntry(
            ApiNewDiaryEntry(
                entryType = ENTRY_TYPE_NOTE,
                text = text
            )
        )
    }

    override suspend fun editEntry(entryId: Long, modifiedText: String) {
        apiDefinition.editDiaryEntry(entryId, ApiNewDiaryEntry(text = modifiedText))
    }

    override suspend fun deleteEntry(entry: DiaryEntry) {
        apiDefinition.deleteDiaryEntry(entry.id)
    }
}