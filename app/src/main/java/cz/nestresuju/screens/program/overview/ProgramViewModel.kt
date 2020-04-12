package cz.nestresuju.screens.program.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.model.repositories.ProgramSecondRepository
import cz.nestresuju.model.repositories.ProgramThirdRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ProgramViewModel(
    private val programFirstRepository: ProgramFirstRepository,
    private val programSecondRepository: ProgramSecondRepository,
    private val programThirdRepository: ProgramThirdRepository
) : BaseViewModel() {

    private val _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateStream: LiveData<ScreenState>
        get() = _screenStateLiveData

    init {
        viewModelScope.launch {
            val programFirstFlow = programFirstRepository.observeProgramResults()
            val programSecondFlow = programSecondRepository.observeProgramResults()
            val programThirdFlow = programThirdRepository.observeProgramResults()

            combine(programFirstFlow, programSecondFlow, programThirdFlow) { firstResults, secondResults, thirdResults ->
                ScreenState(firstResults, secondResults, thirdResults)
            }.collect { screenState ->
                _screenStateLiveData.value = screenState
            }
        }
    }

    data class ScreenState(
        val programFirstResults: ProgramFirstResults,
        val programSecondResults: ProgramSecondResults,
        val programThirdResults: ProgramThirdResults
    )
}