package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for phase
 */
class ProgramFirstSatisfiabilityViewModel(
    private val programRepository: ProgramFirstRepository
) : BaseViewModel() {

    companion object {

        const val MIN_SATISFIABILITY_ALLOWED = 6
    }

    private val _scaleLiveData = MutableLiveData(1)
    val scaleStream: LiveData<Int>
        get() = _scaleLiveData

    init {
        viewModelScope.launch {
            _scaleLiveData.value = programRepository.getProgramResults().satisfiability
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 3) }
        }
    }

    fun onScaleChanged(scale: Int) {
        _scaleLiveData.value = scale

        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(satisfiability = scale) }
        }
    }
}