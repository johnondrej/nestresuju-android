package cz.nestresuju.screens.program.second

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.model.repositories.ProgramOverviewRepository
import cz.nestresuju.model.repositories.ProgramSecondRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

/**
 * ViewModel for last relaxation phase of second program.
 */
class ProgramSecondRelaxationViewModel(
    private val programOverviewRepository: ProgramOverviewRepository,
    private val programRepository: ProgramSecondRepository
) : BaseViewModel() {

    private var startTime: ZonedDateTime = ZonedDateTime.now()

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 7) }
        }
    }

    fun submitRelaxationDuration() {
        GlobalScope.launch {
            if (programRepository.getProgramResults().programCompleted != null) {
                // program already submitted, skip
                return@launch
            }

            val now = ZonedDateTime.now()
            programRepository.updateProgramResults { currentResults ->
                currentResults.copy(relaxationDuration = Duration.between(startTime, now).seconds)
            }
            programOverviewRepository.updateStartDateForProgram(
                ProgramId.PROGRAM_THIRD_ID,
                previousProgramCompletedAt = now,
                completedProgramId = ProgramId.PROGRAM_SECOND_ID
            )
            programRepository.submitResults(programCompletedDate = now)
        }
    }
}