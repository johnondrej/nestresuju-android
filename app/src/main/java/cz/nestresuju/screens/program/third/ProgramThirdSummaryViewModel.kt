package cz.nestresuju.screens.program.third

import androidx.lifecycle.liveData
import cz.nestresuju.model.repositories.ProgramThirdRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for summary of first program.
 */
class ProgramThirdSummaryViewModel(
    private val programRepository: ProgramThirdRepository
) : BaseViewModel() {

    val resultsStream = liveData {
        emit(programRepository.getProgramResults())
    }
}