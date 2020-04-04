package cz.nestresuju.screens.tests.screening

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.model.common.State
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.tests.screening.ScreeningTestOption
import cz.nestresuju.model.repositories.InputTestsRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlin.math.ceil

/**
 * [ViewModel] for screening test.
 */
class ScreeningTestViewModel(
    private val inputTestsRepository: InputTestsRepository
) : BaseViewModel() {

    companion object {

        // How many options fit to a single page
        private const val PAGE_SIZE = 8
    }

    val viewStateStream = StateLiveData<ViewState>()
    val completionEvent = LiveEvent<Unit>()

    private lateinit var options: List<ScreeningTestOption>

    private var latestViewState: ViewState? = null
    private var currentPage = 0

    private val viewState: ViewState
        get() = (viewStateStream.value as State.Loaded<ViewState>).data

    init {
        fetchOptions()
    }

    fun selectOption(optionId: Long) {
        viewState.update { currentState ->
            currentState.copy(
                selectedOptionIds = currentState.selectedOptionIds + optionId
            )
        }
    }

    fun unselectOption(optionId: Long) {
        viewState.update { currentState ->
            currentState.copy(
                selectedOptionIds = currentState.selectedOptionIds - optionId
            )
        }
    }

    fun nextPage() {
        if (currentPage < totalPages() - 1) {
            ++currentPage

            viewState.update { currentState ->
                currentState.copy(
                    options = options.subList(currentPage * PAGE_SIZE, minOf((currentPage + 1) * PAGE_SIZE, options.size)),
                    progress = Progress(
                        currentPage = currentPage,
                        totalPages = totalPages()
                    )
                )
            }
        }
    }

    fun previousPage() {
        if (currentPage > 0) {
            --currentPage

            viewState.update { currentState ->
                currentState.copy(
                    options = options.subList(currentPage * PAGE_SIZE, minOf((currentPage + 1) * PAGE_SIZE, options.size)),
                    progress = Progress(
                        currentPage = currentPage,
                        totalPages = totalPages()
                    )
                )
            }
        }
    }

    fun submitResults() {
        viewModelScope.launchWithErrorHandling {
            viewStateStream.loading()
            try {
                inputTestsRepository.submitScreeningTestResults(results = latestViewState!!.selectedOptionIds.toList())
                completionEvent.value = Unit
            } catch (e: Exception) {
                viewStateStream.loaded(latestViewState!!)
                throw e
            }
        }
    }

    fun tryAgain() {
        if (!::options.isInitialized) {
            fetchOptions()
        } else {
            submitResults()
        }
    }

    fun firstPageSelected() = currentPage == 0

    private fun fetchOptions() {
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(viewStateStream)) {
            viewStateStream.loading()
            options = inputTestsRepository.fetchScreeningTestOptions()
            viewStateStream.loaded(
                ViewState(
                    options = options,
                    selectedOptionIds = emptySet(),
                    progress = Progress(
                        currentPage = 0,
                        totalPages = totalPages()
                    )
                ).also { latestViewState = it }
            )
        }
    }

    private fun totalPages() = ceil(options.size / PAGE_SIZE.toFloat()).toInt()

    private fun ViewState.update(updater: (ViewState) -> ViewState) {
        val newViewState = updater(this)

        viewStateStream.loaded(newViewState)
        latestViewState = newViewState
    }

    data class ViewState(
        val options: List<ScreeningTestOption>,
        val selectedOptionIds: Set<Long>,
        val progress: Progress
    )

    data class Progress(
        val currentPage: Int,
        val totalPages: Int
    ) {

        val isFirst: Boolean
            get() = currentPage == 0

        val isLast: Boolean
            get() = currentPage == totalPages - 1
    }
}