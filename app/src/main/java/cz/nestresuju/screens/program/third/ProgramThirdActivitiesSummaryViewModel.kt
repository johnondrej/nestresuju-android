package cz.nestresuju.screens.program.third

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.repositories.ProgramThirdRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel for screen with user's activities summary in program 3.
 */
class ProgramThirdActivitiesSummaryViewModel(
    private val programRepository: ProgramThirdRepository
) : BaseViewModel() {

    val activitiesStream = liveData<List<ProgramThirdResults.ActivityEntry>> {
        emitSource(programRepository.observeProgramResults()
            .map { results -> results.activities.sortedByDescending { (it.hours * 60) + it.minutes } }
            .asLiveData()
        )
    }

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 4) }
        }
    }
}