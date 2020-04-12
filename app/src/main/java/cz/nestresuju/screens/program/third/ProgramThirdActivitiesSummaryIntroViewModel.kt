package cz.nestresuju.screens.program.third

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.repositories.ProgramThirdRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for screen with activities summary intro in program 3.
 */
class ProgramThirdActivitiesSummaryIntroViewModel(
    private val programRepository: ProgramThirdRepository
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = 3) }
        }
    }
}