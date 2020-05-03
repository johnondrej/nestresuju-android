package cz.nestresuju.networking

import cz.nestresuju.model.entities.api.about.ApiFeedbackRequest
import cz.nestresuju.model.entities.api.about.ContactsResponse
import cz.nestresuju.model.entities.api.about.ResearchResponse
import cz.nestresuju.model.entities.api.auth.LoginChecklistResponse
import cz.nestresuju.model.entities.api.deadline.ProgramDeadlineResponse
import cz.nestresuju.model.entities.api.diary.ApiNewDiaryEntry
import cz.nestresuju.model.entities.api.diary.DiaryEntriesResponse
import cz.nestresuju.model.entities.api.diary.MoodQuestionsResponse
import cz.nestresuju.model.entities.api.library.LibraryResponse
import cz.nestresuju.model.entities.api.program.evaluation.ApiProgramEvaluation
import cz.nestresuju.model.entities.api.program.first.ApiProgramFirstResults
import cz.nestresuju.model.entities.api.program.fourth.ApiProgramFourthResults
import cz.nestresuju.model.entities.api.program.fourth.ProgramFourthQuestionsResponse
import cz.nestresuju.model.entities.api.program.overview.ProgramOverviewResponse
import cz.nestresuju.model.entities.api.program.second.ApiProgramSecondResults
import cz.nestresuju.model.entities.api.program.third.ApiProgramThirdResults
import cz.nestresuju.model.entities.api.tests.input.ApiInputTestResults
import cz.nestresuju.model.entities.api.tests.input.ApiScreeningTestResults
import cz.nestresuju.model.entities.api.tests.input.InputTestQuestionsResponse
import cz.nestresuju.model.entities.api.tests.input.ScreeningTestOptionsResponse
import cz.nestresuju.model.entities.api.tests.output.ApiOutputTestSecondResults
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

    @GET("v1/final-test/first/questions")
    suspend fun getOutputTestQuestions(): InputTestQuestionsResponse

    @POST("v1/final-test/first")
    suspend fun submitOutputTestFirstResults(@Body results: ApiInputTestResults): Response<Unit>

    @POST("v1/final-test/second")
    suspend fun submitOutputTestSecondResults(@Body results: ApiOutputTestSecondResults): Response<Unit>

    @GET("v1/screening-test/options")
    suspend fun getScreeningTestOptions(): ScreeningTestOptionsResponse

    @POST("v1/screening-test")
    suspend fun submitScreeningTestResults(@Body results: ApiScreeningTestResults): Response<Unit>

    @POST("v1/program/evaluate")
    suspend fun submitProgramEvaluation(@Body evaluation: ApiProgramEvaluation)

    @GET("v1/program/state")
    suspend fun getProgramOverview(): ProgramOverviewResponse

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

    @GET("v1/program/searching-for-meaning/questions")
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

    @GET("v1/library")
    suspend fun getLibraryContent(): LibraryResponse

    @GET("v1/about/team")
    suspend fun getContacts(): ContactsResponse

    @GET("v1/about/research")
    suspend fun getResearchInfo(): ResearchResponse

    @GET("v1/program-deadline/state")
    suspend fun getProgramDeadline(): ProgramDeadlineResponse

    @POST("v1/about/feedback")
    suspend fun sendEmailFeedback(@Body feedback: ApiFeedbackRequest): Response<Unit>

    @DELETE("v1/remove-user")
    suspend fun cancelUserAccount(): Response<Unit>
}