package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.DiaryEntitiesConverter
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressQuestion
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository providing all data related to user's diary.
 */
interface DiaryRepository {

    suspend fun fetchMoodQuestions(): List<StressQuestion>

    suspend fun fetchDiaryEntries(moodQuestions: List<StressQuestion>): List<DiaryEntry>
}

class DiaryRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val diaryEntitiesConverter: DiaryEntitiesConverter
) : DiaryRepository {

    override suspend fun fetchMoodQuestions(): List<StressQuestion> {
        return apiDefinition.getMoodQuestions(0).items.map { diaryEntitiesConverter.apiMoodQuestionToDomain(it) }
    }

    override suspend fun fetchDiaryEntries(moodQuestions: List<StressQuestion>): List<DiaryEntry> {
        return apiDefinition.getDiaryEntires(0).items.map {
            diaryEntitiesConverter.apiDiaryEntryToDomain(it, moodQuestions)
        }
    }
}