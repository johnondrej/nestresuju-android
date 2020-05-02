package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.InputTestsConverter
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor
import cz.nestresuju.model.entities.api.tests.input.ApiInputTestResults
import cz.nestresuju.model.entities.api.tests.input.ApiScreeningTestResults
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestion
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestionAnswer
import cz.nestresuju.model.entities.domain.tests.screening.ScreeningTestOption
import cz.nestresuju.model.errors.DuplicitDataException
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository for accessing data related to input and screening tests.
 */
interface InputTestsRepository {

    suspend fun fetchInputTestQuestions(): List<InputTestQuestion>

    suspend fun submitInputTestResults(results: List<InputTestQuestionAnswer>)

    suspend fun fetchOutputTestQuestions(): List<InputTestQuestion>

    suspend fun submitOutputTestResults(results: List<InputTestQuestionAnswer>)

    suspend fun fetchScreeningTestOptions(): List<ScreeningTestOption>

    suspend fun submitScreeningTestResults(results: List<Long>)
}

class InputTestsRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val entityConverter: InputTestsConverter,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : InputTestsRepository {

    override suspend fun fetchInputTestQuestions(): List<InputTestQuestion> {
        return apiDefinition.getInputTestQuestions().items
            .sortedBy { it.order }
            .map { apiQuestion -> entityConverter.apiInputTestQuestionToDomain(apiQuestion) }
    }

    override suspend fun submitInputTestResults(results: List<InputTestQuestionAnswer>) {
        try {
            apiDefinition.submitInputTestResults(
                ApiInputTestResults(
                    results = results.map { answer ->
                        entityConverter.inputTestQuestionAnswerToApi(answer)
                    }
                )
            )
        } catch (e: DuplicitDataException) {
            // do nothing, test was already successfully submitted earlier
        }
        sharedPreferencesInteractor.setInputTestCompleted()
    }

    override suspend fun fetchOutputTestQuestions(): List<InputTestQuestion> {
        return apiDefinition.getOutputTestQuestions().items
            .sortedBy { it.order }
            .map { apiQuestion -> entityConverter.apiInputTestQuestionToDomain(apiQuestion) }
    }

    override suspend fun submitOutputTestResults(results: List<InputTestQuestionAnswer>) {
        try {
            apiDefinition.submitOutputTestFirstResults(
                ApiInputTestResults(
                    results = results.map { answer ->
                        entityConverter.inputTestQuestionAnswerToApi(answer)
                    }
                )
            )
        } catch (e: DuplicitDataException) {
            // do nothing, test was already successfully submitted earlier
        }
        sharedPreferencesInteractor.setOutputTestCompleted()
    }

    override suspend fun fetchScreeningTestOptions(): List<ScreeningTestOption> {
        return apiDefinition.getScreeningTestOptions().items
            .sortedBy { it.order }
            .map { apiOption -> entityConverter.apiScreeningTestOptionToDomain(apiOption) }
    }

    override suspend fun submitScreeningTestResults(results: List<Long>) {
        try {
            apiDefinition.submitScreeningTestResults(
                ApiScreeningTestResults(selectedOptionIds = results)
            )
        } catch (e: DuplicitDataException) {
            // do nothing, test was already successfully submitted earlier
        }
        sharedPreferencesInteractor.setScreeningTestCompleted()
    }
}