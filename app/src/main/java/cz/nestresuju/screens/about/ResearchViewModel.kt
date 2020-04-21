package cz.nestresuju.screens.about

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.domain.ResearchInfo
import cz.nestresuju.model.repositories.AboutAppRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for research info screen.
 */
class ResearchViewModel(
    private val aboutAppRepository: AboutAppRepository
) : BaseViewModel() {

    val researchInfoStream = StateLiveData<ResearchInfo>()

    init {
        fetchResearchInfo()
    }

    fun fetchResearchInfo() {
        researchInfoStream.loading()
        viewModelScope.launchWithErrorHandling {
            try {
                aboutAppRepository.fetchResearchInfo()
            } catch (e: Exception) {
                // Rethrow exception to allow further error handling, but still show data from DB
                researchInfoStream.loaded(aboutAppRepository.getResearchInfo())
                throw e
            }
            researchInfoStream.loaded(aboutAppRepository.getResearchInfo())
        }
    }
}