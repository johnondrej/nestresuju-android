package cz.nestresuju.networking

import cz.nestresuju.model.entities.api.auth.LoginChecklistResponse
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.api.diary.DiaryEntriesResponse
import cz.nestresuju.model.entities.api.diary.MoodQuestionsResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Definition of API endpoints.
 */
interface ApiDefinition {

    @GET("v1/user-checklist")
    suspend fun getLoginPrerequirements(): LoginChecklistResponse

    @PUT("v1/user-consent/give")
    suspend fun giveUserConsent(): Response<Unit>

    @GET("v1/diary/mood-questions")
    suspend fun getDiaryMoodQuestions(@Query("timestamp") timestamp: Long): MoodQuestionsResponse

    @GET("v1/diary/entries")
    suspend fun getDiaryEntries(@Query("timestamp") timestamp: Long): DiaryEntriesResponse

    @POST("v1/diary/entries")
    suspend fun createNewDiaryEntry(@Body entry: ApiNewDiaryEntry)

    @PATCH("v1/diary/entries/{entryId}")
    suspend fun editDiaryEntry(@Path("entryId") entryId: Long, @Body entry: ApiNewDiaryEntry): Response<Unit>

    @DELETE("v1/diary/entries/{entryId}")
    suspend fun deleteDiaryEntry(@Path("entryId") entryId: Long): Response<Unit>
}