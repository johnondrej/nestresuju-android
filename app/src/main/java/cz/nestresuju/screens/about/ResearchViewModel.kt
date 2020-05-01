package cz.nestresuju.screens.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.State
import cz.nestresuju.model.entities.domain.domain.ResearchInfo
import cz.nestresuju.model.repositories.AboutAppRepository
import cz.nestresuju.networking.ApiDefinition
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for research info screen.
 */
class ResearchViewModel(
    private val aboutAppRepository: AboutAppRepository,
    private val apiDefinition: ApiDefinition
) : BaseViewModel() {

    private val _screenStateLiveData = MutableLiveData(
        ScreenState(
            researchInfoState = State.Idle,
            cancelAccountState = State.Idle
        )
    )
    val screenStateStream: LiveData<ScreenState>
        get() = _screenStateLiveData

    init {
        fetchResearchInfo()
    }

    fun fetchResearchInfo() {
        updateScreenState { it.copy(researchInfoState = State.Loading) }
        viewModelScope.launchWithErrorHandling {
            try {
                aboutAppRepository.fetchResearchInfo()
            } catch (e: Exception) {
                // Rethrow exception to allow further error handling, but still show data from DB
                val data = aboutAppRepository.getResearchInfo()
                updateScreenState { it.copy(researchInfoState = State.Loaded(data)) }
                throw e
            }
            val data = aboutAppRepository.getResearchInfo()
            updateScreenState { it.copy(researchInfoState = State.Loaded(data)) }
        }
    }

    fun cancelAccount() {
        updateScreenState { it.copy(cancelAccountState = State.Loading) }
        viewModelScope.launchWithErrorHandling {
            try {
                apiDefinition.cancelUserAccount()
                updateScreenState { it.copy(cancelAccountState = State.Loaded(Unit)) }
            } catch (e: Exception) {
                // Rethrow exception to allow further error handling, but show content again
                updateScreenState { it.copy(cancelAccountState = State.Idle) }
                throw e
            }
        }
    }

    private fun updateScreenState(updater: (ScreenState) -> ScreenState) {
        _screenStateLiveData.value = updater(_screenStateLiveData.value!!)
    }

    data class ScreenState(
        val researchInfoState: State<ResearchInfo>,
        val cancelAccountState: State<Unit>
    )
}