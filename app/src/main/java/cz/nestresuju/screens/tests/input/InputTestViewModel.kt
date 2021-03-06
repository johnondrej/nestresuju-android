package cz.nestresuju.screens.tests.input

import androidx.lifecycle.ViewModel
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestion
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestionAnswer
import cz.nestresuju.model.repositories.InputTestsRepository
import cz.nestresuju.screens.tests.BaseTestViewModel

/**
 * [ViewModel] for input test screen.
 */
class InputTestViewModel(
    private val inputTestsRepository: InputTestsRepository
) : BaseTestViewModel() {

    override suspend fun fetchTestQuestions(): List<InputTestQuestion> {
        return inputTestsRepository.fetchInputTestQuestions()
    }

    override suspend fun submitTestResults(results: List<InputTestQuestionAnswer>) {
        inputTestsRepository.submitInputTestResults(results)
    }
}