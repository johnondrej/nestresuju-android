package cz.nestresuju.model.entities.domain.program.fourth

/**
 * Entity representing input question in fourth program.
 */
data class ProgramFourthQuestion(
    val id: Long,
    val order: Int,
    val type: QuestionType,
    val text: String,
    val answer: ProgramFourthAnswer?
) {

    enum class QuestionType {
        PRESENT,
        SEARCHING,
        NOT_SCORED
    }
}