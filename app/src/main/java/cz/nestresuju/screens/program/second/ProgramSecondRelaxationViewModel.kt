package cz.nestresuju.screens.program.second

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.repositories.ProgramSecondRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

/**
 * ViewModel for last relaxation phase of second program.
 */
class ProgramSecondRelaxationViewModel(
    private val programRepository: ProgramSecondRepository
) : BaseViewModel() {

    private var startTime: LocalDateTime = LocalDateTime.now()

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 7) }
        }
    }

    fun submitRelaxationDuration() {
        GlobalScope.launch {
            val now = LocalDateTime.now()
            programRepository.updateProgramResults { currentResults ->
                currentResults.copy(relaxationDuration = Duration.between(startTime, now).seconds)
            }
            programRepository.submitResults()
        }
    }
}