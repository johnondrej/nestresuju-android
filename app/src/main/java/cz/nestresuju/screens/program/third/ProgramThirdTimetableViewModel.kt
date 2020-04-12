package cz.nestresuju.screens.program.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.repositories.ProgramThirdRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for screen with managing user's timetable in program 3.
 */
class ProgramThirdTimetableViewModel(
    private val programRepository: ProgramThirdRepository
) : BaseViewModel() {

    private val _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateStream: LiveData<ScreenState>
        get() = _screenStateLiveData

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 1) }

            programRepository.observeProgramResults().collect { results ->
                val timetable = results.timetable

                _screenStateLiveData.value = ScreenState(
                    gettingUpTime = formatTime(ProgramThirdHourActivity.GETTING_UP, timetable),
                    breakfastTime = formatTime(ProgramThirdHourActivity.BREAKFAST, timetable),
                    lunchTime = formatTime(ProgramThirdHourActivity.LUNCH, timetable),
                    dinnerTime = formatTime(ProgramThirdHourActivity.DINNER, timetable),
                    goingSleepTime = formatTime(ProgramThirdHourActivity.GOING_SLEEP, timetable),
                    sleepingTime = formatTime(ProgramThirdHourActivity.SLEEPING, timetable)
                )
            }
        }
    }

    fun onActivityTimeSet(activity: ProgramThirdHourActivity, hour: Int, minute: Int) {
        viewModelScope.launch {
            programRepository.updateTimetableActivity(activity.txtId, hour, minute)
        }
    }

    fun onActivityTimeNotSet(activity: ProgramThirdHourActivity) {
        viewModelScope.launch {
            programRepository.updateTimetableActivity(activity.txtId, hour = null, minute = null)
        }
    }

    data class ScreenState(
        val gettingUpTime: String? = null,
        val breakfastTime: String? = null,
        val lunchTime: String? = null,
        val dinnerTime: String? = null,
        val goingSleepTime: String? = null,
        val sleepingTime: String? = null
    ) {

        val canContinue: Boolean
            get() = gettingUpTime != null && goingSleepTime != null && sleepingTime != null
    }

    private fun formatTime(activity: ProgramThirdHourActivity, timetable: List<ProgramThirdResults.HourEntry>): String? {
        val entry = timetable.find { it.name == activity.txtId }
        val format = "%02d:%02d"

        return if (entry?.hour != null && entry.minute != null) {
            String.format(format, entry.hour, entry.minute)
        } else {
            null
        }
    }
}