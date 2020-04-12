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
 * ViewModel for screen with managing user's activities in program 3.
 */
class ProgramThirdActivitiesViewModel(
    private val programRepository: ProgramThirdRepository
) : BaseViewModel() {

    val activitiesStream = liveData<List<ProgramThirdResults.ActivityEntry>> {
        emitSource(programRepository.observeProgramResults()
            .map { results -> results.activities.sortedWith(compareBy({ it.userDefined }, { it.name })) }
            .asLiveData()
        )
    }

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 2) }
        }
    }

    fun onActivityAdded(activityName: String, hours: Int, minutes: Int) {
        viewModelScope.launch {
            programRepository.updateActivity(activityName, userDefined = true, hours = hours, minutes = minutes)
        }
    }

    fun onActivityTimeUpdated(activityName: String, userDefined: Boolean, hours: Int, minutes: Int) {
        viewModelScope.launch {
            programRepository.updateActivity(activityName, userDefined, hours, minutes)
        }
    }

    fun onActivityRemoved(activityName: String) {
        viewModelScope.launch {
            programRepository.removeActivity(activityName)
        }
    }
}