package cz.nestresuju.screens.about

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.EmptyStateLiveData
import cz.nestresuju.model.entities.api.about.ApiFeedbackRequest
import cz.nestresuju.networking.ApiDefinition
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for screen for sending app feedback.
 */
class FeedbackViewModel(
    private val apiDefinition: ApiDefinition
) : BaseViewModel() {

    val completionStream = EmptyStateLiveData()

    fun sendFeedback(text: String) {
        completionStream.loading()
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(completionStream)) {
            apiDefinition.sendEmailFeedback(ApiFeedbackRequest(text))
            completionStream.loaded()
        }
    }
}