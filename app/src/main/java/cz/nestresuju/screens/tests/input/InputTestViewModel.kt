package cz.nestresuju.screens.tests.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.model.common.State
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.tests.input.InputTestAnswer
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestion
import cz.nestresuju.model.entities.domain.tests.input.InputTestQuestionAnswer
import cz.nestresuju.model.repositories.InputTestsRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * [ViewModel] for input test screen.
 */
class InputTestViewModel(
    private val inputTestsRepository: InputTestsRepository
) : BaseViewModel() {

    val viewStateStream = StateLiveData<ViewState>()
    val completionEvent = LiveEvent<Unit>()

    private lateinit var questions: List<InputTestQuestion>

    private var latestViewState: ViewState? = null

    private val answers = mutableMapOf<Int, InputTestAnswer>()
    private val viewState: ViewState
        get() = (viewStateStream.value as State.Loaded<ViewState>).data

    init {
        fetchInputQuestions()
    }

    fun selectAnswer(answer: InputTestAnswer) {
        if (viewStateStream.value is State.Loaded) {
            answers[viewState.progress.currentQuestion] = answer
            viewState.update { currentState -> currentState.copy(answer = answer) }
        }
    }

    fun nextQuestion() {
        val nextQuestionIndex = viewState.progress.currentQuestion + 1
        val nextQuestion = questions[nextQuestionIndex]
        val updatedProgress = viewState.progress.copy(currentQuestion = nextQuestionIndex)

        viewState.update { currentState ->
            currentState.copy(
                question = nextQuestion,
                answer = answers[nextQuestionIndex],
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
                answer = answers[previousQuestionIndex],
                progress = updatedProgress
            )
        }
    }

    fun submitResults() {
        viewModelScope.launchWithErrorHandling {
            val results = answers.map { (questionIndex: Int, answer: InputTestAnswer) ->
                InputTestQuestionAnswer(
                    questionId = questions[questionIndex].id,
                    answer = answer
                )
            }

            viewStateStream.loading()
            try {
                inputTestsRepository.submitInputTestResults(results)
                completionEvent.value = Unit
            } catch (e: Exception) {
                viewStateStream.loaded(latestViewState!!)
                throw e
            }
        }
    }

    fun tryAgain() {
        if (!::questions.isInitialized) {
            fetchInputQuestions()
        } else {
            submitResults()
        }
    }

    fun firstQuestionSelected() = (viewStateStream.value as? State.Loaded)?.data?.progress?.isFirst ?: true

    private fun fetchInputQuestions() {
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(viewStateStream)) {
            viewStateStream.loading()
            questions = inputTestsRepository.fetchInputTestQuestions()
            viewStateStream.loaded(
                ViewState(
                    question = questions.first(),
                    answer = null,
                    progress = Progress(
                        currentQuestion = 0,
                        totalQuestions = questions.size
                    )
                ).also { latestViewState = it }
            )
        }
    }

    private fun ViewState.update(updater: (ViewState) -> ViewState) {
        val newViewState = updater(this)

        viewStateStream.loaded(newViewState)
        latestViewState = newViewState
    }

    data class ViewState(
        val question: InputTestQuestion,
        val answer: InputTestAnswer?,
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