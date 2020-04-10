package cz.nestresuju.screens.program.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProgramViewModel(
    private val programFirstRepository: ProgramFirstRepository
) : BaseViewModel() {

    private val _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateStream: LiveData<ScreenState>
        get() = _screenStateLiveData

    init {
        viewModelScope.launch {
            programFirstRepository.observeProgramResults().collect { results ->
                _screenStateLiveData.value = ScreenState(results)
            }
        }
    }

    data class ScreenState(
        val programFirstResults: ProgramFirstResults
    )
}