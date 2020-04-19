package cz.nestresuju.screens.program.fourth

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.EmptyStateLiveData
import cz.nestresuju.model.repositories.ProgramFourthRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for screen with instructions about filling in questionnaire from program 4.
 */
class ProgramFourthQuestionsIntroViewModel(
    private val programRepository: ProgramFourthRepository
) : BaseViewModel() {

    val loadingStateStream = EmptyStateLiveData()

    init {
        fetchProgramQuestions()

        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 3) }
        }
    }

    private fun fetchProgramQuestions() {
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(loadingStateStream)) {
            val questions = programRepository.getProgramResults().questions

            if (questions.isEmpty()) {
                loadingStateStream.loading()
                programRepository.fetchQuestions()
                loadingStateStream.loaded()
            } else {
                loadingStateStream.loaded()
            }
        }
    }

    fun tryAgain() {
        fetchProgramQuestions()
    }
}