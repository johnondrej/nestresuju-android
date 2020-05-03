package cz.nestresuju.screens.tests.output

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.EmptyStateLiveData
import cz.nestresuju.model.entities.api.tests.output.ApiOutputTestSecondResults
import cz.nestresuju.model.entities.domain.tests.input.InputTestAnswer
import cz.nestresuju.model.repositories.InputTestsRepository
import cz.nestresuju.screens.base.BaseViewModel
import org.threeten.bp.ZonedDateTime

/**
 * [ViewModel] for second output test screen.
 */
class OutputTestSecondViewModel(
    private val inputTestsRepository: InputTestsRepository
) : BaseViewModel() {

    private val _screenStateLiveData = MutableLiveData(ScreenState())
    val screenStateStream: LiveData<ScreenState>
        get() = _screenStateLiveData

    val completionStream = EmptyStateLiveData()

    private val screenState: ScreenState
        get() = _screenStateLiveData.value!!

    fun stressManagementBeforeChanged(answer: InputTestAnswer) {
        screenState.update { it.copy(stressManagementBefore = answer) }
    }

    fun stressManagementAfterChanged(answer: InputTestAnswer) {
        screenState.update { it.copy(stressManagementAfter = answer) }
    }

    fun appHelpfulAnswerChanged(answer: Boolean) {
        screenState.update { it.copy(wasAppHelpful = answer) }
    }

    fun helpCommentChanged(answer: String) {
        screenState.update { it.copy(helpComment = answer) }
    }

    fun appRatingChanged(rating: Int) {
        screenState.update { it.copy(appRating = rating) }
    }

    fun appRatingCommentChanged(answer: String) {
        screenState.update { it.copy(appRatingComment = answer) }
    }

    fun recommendationChanged(answer: String) {
        screenState.update { it.copy(recommendation = answer) }
    }

    fun sendResults() {
        completionStream.loading()
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(completionStream)) {
            val state = screenState
            val results = ApiOutputTestSecondResults(
                stressManagementBefore = state.stressManagementBefore!!.intValue + 1,
                stressManagementAfter = state.stressManagementAfter!!.intValue + 1,
                wasAppHelpful = state.wasAppHelpful!!,
                helpComment = if (state.wasAppHelpful) state.helpComment else null,
                appRating = state.appRating!!,
                appRatingComment = state.appRatingComment,
                recommendation = state.recommendation,
                programCompletedDate = ZonedDateTime.now()
            )
            inputTestsRepository.submitOutputTestSecondResults(results)
            completionStream.loaded()
        }
    }

    private fun ScreenState.update(updater: (ScreenState) -> ScreenState) {
        _screenStateLiveData.value = updater(this)
    }

    data class ScreenState(
        val stressManagementBefore: InputTestAnswer? = null,
        val stressManagementAfter: InputTestAnswer? = null,
        val wasAppHelpful: Boolean? = null,
        val helpComment: String? = null,
        val appRating: Int? = null,
        val appRatingComment: String? = null,
        val recommendation: String? = null
    )
}