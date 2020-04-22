package cz.nestresuju.screens.program.overview

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthResults
import cz.nestresuju.model.entities.domain.program.overview.ProgramOverview
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.repositories.*
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ProgramViewModel(
    private val programOverviewRepository: ProgramOverviewRepository,
    private val programFirstRepository: ProgramFirstRepository,
    private val programSecondRepository: ProgramSecondRepository,
    private val programThirdRepository: ProgramThirdRepository,
    private val programFourthRepository: ProgramFourthRepository
) : BaseViewModel() {

    val screenStateStream = StateLiveData<ScreenState>()

    init {
        screenStateStream.loading()
        viewModelScope.launch {
            try {
                programOverviewRepository.fetchOverview()
            } catch (e: Exception) {
                // do nothing, data will be updated next time
            }

            val programOverviewFlow = programOverviewRepository.observeOverview()
            val programFirstFlow = programFirstRepository.observeProgramResults()
            val programSecondFlow = programSecondRepository.observeProgramResults()
            val programThirdFlow = programThirdRepository.observeProgramResults()
            val programFourthFlow = programFourthRepository.observeProgramResults()

            combine(
                programOverviewFlow,
                programFirstFlow,
                programSecondFlow,
                programThirdFlow,
                programFourthFlow
            ) { overview, firstResults, secondResults, thirdResults, fourthResults ->
                ScreenState(overview, firstResults, secondResults, thirdResults, fourthResults)
            }.collect { screenState ->
                screenStateStream.loaded(screenState)
            }
        }
    }

    data class ScreenState(
        val overview: List<ProgramOverview>,
        val programFirstResults: ProgramFirstResults,
        val programSecondResults: ProgramSecondResults,
        val programThirdResults: ProgramThirdResults,
        val programFourthResults: ProgramFourthResults
    )
}