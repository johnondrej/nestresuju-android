package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.model.repositories.ProgramOverviewRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

/**
 * ViewModel for last screen of program 1.
 */
class ProgramFirstOverviewViewModel(
    private val programOverviewRepository: ProgramOverviewRepository,
    private val programRepository: ProgramFirstRepository
) : BaseViewModel() {

    val resultsStream = liveData {
        emit(programRepository.getProgramResults())
    }

    private val _summaryLiveData = MutableLiveData("")
    val summaryStream: LiveData<String>
        get() = _summaryLiveData

    init {
        viewModelScope.launchWithErrorHandling {
            val results = programRepository.getProgramResults()
            _summaryLiveData.value = results.summarizedTarget

            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 6) }
        }
    }

    fun onSummaryChanged(summary: String) {
        _summaryLiveData.value = summary
        viewModelScope.launchWithErrorHandling(Dispatchers.IO) {
            // debounce
            delay(300)
            if (summaryStream.value == summary) {
                programRepository.updateProgramResults { currentResults -> currentResults.copy(summarizedTarget = summary) }
            }
        }
    }

    fun submitResults() {
        GlobalScope.launch {
            val now = ZonedDateTime.now()
            programRepository.submitResults(programCompletedDate = now)
            programOverviewRepository.updateStartDateForProgram(
                ProgramId.PROGRAM_SECOND_ID,
                previousProgramCompletedAt = now,
                completedProgramId = ProgramId.PROGRAM_FIRST_ID
            )
        }
    }
}