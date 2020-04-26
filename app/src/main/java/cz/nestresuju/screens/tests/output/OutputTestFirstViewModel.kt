package cz.nestresuju.screens.tests.output

import androidx.lifecycle.ViewModel
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestion
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestionAnswer
import cz.nestresuju.model.repositories.InputTestsRepository
import cz.nestresuju.screens.tests.BaseTestViewModel

/**
 * [ViewModel] for output test screen.
 */
class OutputTestFirstViewModel(
    private val inputTestsRepository: InputTestsRepository
) : BaseTestViewModel() {

    override suspend fun fetchTestQuestions(): List<InputTestQuestion> {
        return inputTestsRepository.fetchOutputTestQuestions()
    }

    override suspend fun submitTestResults(results: List<InputTestQuestionAnswer>) {
        inputTestsRepository.submitOutputTestResults(results)
    }
}