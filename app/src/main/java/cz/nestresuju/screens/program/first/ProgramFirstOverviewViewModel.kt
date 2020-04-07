package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for last screen of program 1.
 */
class ProgramFirstOverviewViewModel(
    private val programRepository: ProgramFirstRepository
) : BaseViewModel() {

    private val _resultsLiveData = MutableLiveData<ProgramFirstResults>()
    val resultsStream: LiveData<ProgramFirstResults>
        get() = _resultsLiveData

    private val _summaryLiveData = MutableLiveData("")
    val summaryStream: LiveData<String>
        get() = _summaryLiveData

    init {
        viewModelScope.launch {
            val results = programRepository.getProgramResults()
            _resultsLiveData.value = results
            _summaryLiveData.value = results.summarizedTarget

            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 6) }
        }
    }

    fun onSummaryChanged(summary: String) {
        _summaryLiveData.value = summary
        viewModelScope.launch(Dispatchers.IO) {
            // debounce
            delay(300)
            if (summaryStream.value == summary) {
                programRepository.updateProgramResults { currentResults -> currentResults.copy(summarizedTarget = summary) }
            }
        }
    }
}