package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.InputTestsConverter
import cz.nestresuju.model.entities.api.tests.input.ApiInputTestResults
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestion
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestionAnswer
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository for accessing data related to input and screening tests.
 */
interface InputTestsRepository {

    suspend fun fetchInputTestQuestions(): List<InputTestQuestion>

    suspend fun submitInputTestResults(results: List<InputTestQuestionAnswer>)
}

class InputTestsRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val entityConverter: InputTestsConverter
) : InputTestsRepository {

    override suspend fun fetchInputTestQuestions(): List<InputTestQuestion> {
        return apiDefinition.getInputTestQuestions().items
            .map { apiQuestion -> entityConverter.apiInputTestQuestionToDomain(apiQuestion) }
    }

    override suspend fun submitInputTestResults(results: List<InputTestQuestionAnswer>) {
        apiDefinition.submitInputTestResults(
            ApiInputTestResults(
                results = results.map { answer ->
                    entityConverter.inputTestQuestionAnswerToApi(answer)
                }
            )
        )
    }
}