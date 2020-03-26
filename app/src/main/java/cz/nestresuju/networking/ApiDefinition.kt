package cz.nestresuju.networking

import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.api.diary.DiaryEntriesResponse
import cz.nestresuju.model.entities.api.diary.MoodQuestionsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Definition of API endpoints.
 */
interface ApiDefinition {

    @GET("v1/diary/questions")
    suspend fun getMoodQuestions(@Query("timestamp") timestamp: Long): MoodQuestionsResponse

    @GET("v1/diary/entries")
    suspend fun getDiaryEntires(@Query("timestamp") timestamp: Long): DiaryEntriesResponse

    @POST("v1/diary/entries")
    suspend fun createNewEntry(@Body entry: ApiNewDiaryEntry)
}