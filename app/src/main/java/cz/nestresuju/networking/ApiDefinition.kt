package cz.nestresuju.networking

import cz.nestresuju.model.entities.api.about.ContactsResponse
import cz.nestresuju.model.entities.api.about.ResearchResponse
import cz.nestresuju.model.entities.api.auth.LoginChecklistResponse
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.api.diary.DiaryEntriesResponse
import cz.nestresuju.model.entities.api.diary.MoodQuestionsResponse
import cz.nestresuju.model.entities.api.program.evaluation.ApiProgramEvaluation
import cz.nestresuju.model.entities.api.program.first.ApiProgramFirstResults
import cz.nestresuju.model.entities.api.program.fourth.ApiProgramFourthResults
import cz.nestresuju.model.entities.api.program.fourth.ProgramFourthQuestionsResponse
import cz.nestresuju.model.entities.api.program.second.ApiProgramSecondResults
import cz.nestresuju.model.entities.api.program.third.ApiProgramThirdResults
import cz.nestresuju.model.entities.api.tests.input.ApiInputTestResults
import cz.nestresuju.model.entities.api.tests.input.ApiScreeningTestResults
import cz.nestresuju.model.entities.api.tests.input.InputTestQuestionsResponse
import cz.nestresuju.model.entities.api.tests.input.ScreeningTestOptionsResponse
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

    @GET("v1/input-test/questions")
    suspend fun getInputTestQuestions(): InputTestQuestionsResponse

    @POST("v1/input-test")
    suspend fun submitInputTestResults(@Body results: ApiInputTestResults): Response<Unit>

    @GET("v1/screening-test/options")
    suspend fun getScreeningTestOptions(): ScreeningTestOptionsResponse

    @POST("v1/screening-test")
    suspend fun submitScreeningTestResults(@Body results: ApiScreeningTestResults): Response<Unit>

    @POST("v1/program/evaluation")
    suspend fun submitProgramEvaluation(@Query("programTitle") programId: String, @Body evaluation: ApiProgramEvaluation)

    @GET("v1/program/goal")
    suspend fun getFirstProgramResults(): ApiProgramFirstResults

    @POST("v1/program/goal")
    suspend fun submitFirstProgramResults(@Body results: ApiProgramFirstResults): Response<Unit>

    @GET("v1/program/relaxation")
    suspend fun getSecondProgramResults(): ApiProgramSecondResults

    @POST("v1/program/relaxation")
    suspend fun submitSecondProgramResults(@Body results: ApiProgramSecondResults): Response<Unit>

    @GET("v1/program/time-management")
    suspend fun getThirdProgramResults(): ApiProgramThirdResults

    @POST("v1/program/time-management")
    suspend fun submitThirdProgramResults(@Body results: ApiProgramThirdResults): Response<Unit>

    @GET("v1/program/sarching-for-meaning/questions")
    suspend fun getFourthProgramQuestions(@Query("timestamp") timestamp: Long): ProgramFourthQuestionsResponse

    @GET("v1/program/searching-for-meaning")
    suspend fun getFourthProgramResults(): ApiProgramFourthResults

    @POST("v1/program/searching-for-meaning")
    suspend fun submitFourthProgramResults(@Body results: ApiProgramFourthResults): Response<Unit>

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

    @GET("v1/about/team")
    suspend fun getContacts(): ContactsResponse

    @GET("v1/about/research")
    suspend fun getResearchInfo(): ResearchResponse
}