package cz.nestresuju.model.entities.domain.tests.input

/**
 * Entity representing answer to an input test question.
 */
data class InputTestQuestionAnswer(
    val questionId: Long,
    val answer: InputTestAnswer
)