package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.tests.input.ApiInputTestResults
import cz.nestresuju.model.entities.api.tests.input.InputTestQuestionsResponse
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestion
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestionAnswer

/**
 * Converter for entities related to input tests.
 */
interface InputTestsConverter {

    fun apiInputTestQuestionToDomain(apiInputTestQuestion: InputTestQuestionsResponse.ApiInputTestQuestion): InputTestQuestion

    fun inputTestQuestionAnswerToApi(questionAnswer: InputTestQuestionAnswer): ApiInputTestResults.ApiInputTestQuestionAnswer
}

class InputTestsConverterImpl : InputTestsConverter {

    override fun apiInputTestQuestionToDomain(apiInputTestQuestion: InputTestQuestionsResponse.ApiInputTestQuestion): InputTestQuestion {
        return InputTestQuestion(apiInputTestQuestion.id, apiInputTestQuestion.text)
    }

    override fun inputTestQuestionAnswerToApi(questionAnswer: InputTestQuestionAnswer): ApiInputTestResults.ApiInputTestQuestionAnswer {
        return ApiInputTestResults.ApiInputTestQuestionAnswer(questionAnswer.questionId, questionAnswer.answer.intValue)
    }
}