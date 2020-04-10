package cz.nestresuju.screens.program.second

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.repositories.ProgramSecondRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Common ViewModel for instructions in second program.
 */
class ProgramSecondInstructionsViewModel(
    private val progress: Int,
    private val programRepository: ProgramSecondRepository
) : BaseViewModel() {

    fun updateProgress() {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = progress) }
        }
    }
}