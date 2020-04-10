package cz.nestresuju.screens.program.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.model.repositories.ProgramSecondRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ProgramViewModel(
    private val programFirstRepository: ProgramFirstRepository,
    private val programSecondRepository: ProgramSecondRepository
) : BaseViewModel() {

    private val _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateStream: LiveData<ScreenState>
        get() = _screenStateLiveData

    init {
        viewModelScope.launch {
            val programFirstFlow = programFirstRepository.observeProgramResults()
            val programSecondFlow = programSecondRepository.observeProgramResults()

            combine(programFirstFlow, programSecondFlow) { firstResults, secondResults ->
                ScreenState(firstResults, secondResults)
            }.collect { screenState ->
                _screenStateLiveData.value = screenState
            }
        }
    }

    data class ScreenState(
        val programFirstResults: ProgramFirstResults,
        val programSecondResults: ProgramSecondResults
    )
}