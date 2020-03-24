package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.DiaryEntitiesConverter
import cz.nestresuju.model.entities.api.MoodQuestionsResponse
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressQuestion
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository providing all data related to user's diary.
 */
interface DiaryRepository {

    suspend fun fetchMoodQuestions(): List<StressQuestion>

    suspend fun fetchDiaryEntries(): List<DiaryEntry>
}

class DiaryRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val diaryEntitiesConverter: DiaryEntitiesConverter
) : DiaryRepository {

    override suspend fun fetchMoodQuestions(): List<StressQuestion> {
        return fetchMoodQuestionsFromApi().map { diaryEntitiesConverter.apiMoodQuestionToDomain(it) }
    }

    override suspend fun fetchDiaryEntries(): List<DiaryEntry> {
        return apiDefinition.getDiaryEntires(0).items.map {
            diaryEntitiesConverter.apiDiaryEntryToDomain(it, fetchMoodQuestionsFromApi())
        }
    }

    private fun fetchMoodQuestionsFromApi(): List<MoodQuestionsResponse.ApiStressQuestion> {
        return listOf(
            MoodQuestionsResponse.ApiStressQuestion(id = 0, moodLevel = 1, text = "Proč jsi tak vystresovaný?"),
            MoodQuestionsResponse.ApiStressQuestion(id = 1, moodLevel = 1, text = "Co tě dnes tak vystresovalo?"),
            MoodQuestionsResponse.ApiStressQuestion(id = 2, moodLevel = 2, text = "Co by ti dnes pomohlo se stresem?"),
            MoodQuestionsResponse.ApiStressQuestion(id = 3, moodLevel = 3, text = "Co ti dnes zvedlo náladu?"),
            MoodQuestionsResponse.ApiStressQuestion(id = 4, moodLevel = 4, text = "Bravo! Co ti pomáhá zvládat stres?"),
            MoodQuestionsResponse.ApiStressQuestion(id = 5, moodLevel = 4, text = "Proč jsi dnes úplně bez stresu?")
        )
    }
}