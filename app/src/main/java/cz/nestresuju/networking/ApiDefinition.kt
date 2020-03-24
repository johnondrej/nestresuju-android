package cz.nestresuju.networking

import cz.nestresuju.model.entities.api.DiaryEntriesResponse
import cz.nestresuju.model.entities.api.MoodQuestionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of API endpoints.
 */
interface ApiDefinition {

    @GET("v1/diary/mood-questions")
    suspend fun getMoodQuestions(@Query("timestamp") timestamp: Long): MoodQuestionsResponse

    @GET("v1/diary/entries")
    suspend fun getDiaryEntires(@Query("timestamp") timestamp: Long): DiaryEntriesResponse
}