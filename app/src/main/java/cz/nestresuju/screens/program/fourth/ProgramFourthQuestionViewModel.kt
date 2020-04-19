package cz.nestresuju.screens.program.fourth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthAnswer
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthQuestion
import cz.nestresuju.model.repositories.ProgramFourthRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * [ViewModel] for questions from program 4.
 */
class ProgramFourthQuestionViewModel(
    private val programRepository: ProgramFourthRepository
) : BaseViewModel() {

    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateStream: LiveData<ViewState>
        get() = _viewStateLiveData

    private lateinit var questions: List<ProgramFourthQuestion>

    private var latestViewState: ViewState? = null

    private val viewState: ViewState
        get() = viewStateStream.value!!

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 4) }
            questions = programRepository.getProgramResults().questions.sortedBy { it.order }
            _viewStateLiveData.value = ViewState(
                question = questions[0],
                answer = questions[0].answer,
                progress = Progress(
                    currentQuestion = 0,
                    totalQuestions = questions.size
                )
            )
        }
    }

    fun selectAnswer(answer: ProgramFourthAnswer) {
        val currentQuestion = questions[viewState.progress.currentQuestion]

        questions = questions.map { question ->
            if (question.id == currentQuestion.id) {
                question.copy(answer = answer)
            } else {
                question
            }
        }
        viewState.update { currentState -> currentState.copy(answer = answer) }
        viewModelScope.launch {
            programRepository.updateQuestionAnswer(currentQuestion.id, answer = answer.intValue)
        }
    }

    fun nextQuestion() {
        val nextQuestionIndex = viewState.progress.currentQuestion + 1
        val nextQuestion = questions[nextQuestionIndex]
        val updatedProgress = viewState.progress.copy(currentQuestion = nextQuestionIndex)

        viewState.update { currentState ->
            currentState.copy(
                question = nextQuestion,
                answer = questions[nextQuestionIndex].answer,
                progress = updatedProgress
            )
        }
    }

    fun previousQuestion() {
        val previousQuestionIndex = viewState.progress.currentQuestion - 1
        val previousQuestion = questions[previousQuestionIndex]
        val updatedProgress = viewState.progress.copy(currentQuestion = previousQuestionIndex)

        viewState.update { currentState ->
            currentState.copy(
                question = previousQuestion,
                answer = questions[previousQuestionIndex].answer,
                progress = updatedProgress
            )
        }
    }

    fun submitResults() {
        GlobalScope.launch {
            programRepository.submitResults()
        }
    }

    fun firstQuestionSelected() = viewStateStream.value?.progress?.isFirst ?: true

    private fun ViewState.update(updater: (ViewState) -> ViewState) {
        val newViewState = updater(this)

        _viewStateLiveData.value = newViewState
        latestViewState = newViewState
    }

    data class ViewState(
        val question: ProgramFourthQuestion,
        val answer: ProgramFourthAnswer?,
        val progress: Progress
    )

    data class Progress(
        val currentQuestion: Int,
        val totalQuestions: Int
    ) {

        val isFirst: Boolean
            get() = currentQuestion == 0

        val isLast: Boolean
            get() = currentQuestion == totalQuestions - 1
    }
}