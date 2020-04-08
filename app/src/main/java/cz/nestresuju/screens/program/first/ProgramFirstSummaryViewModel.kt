package cz.nestresuju.screens.program.first

import androidx.lifecycle.liveData
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for summary of first program.
 */
class ProgramFirstSummaryViewModel(
    private val programRepository: ProgramFirstRepository
) : BaseViewModel() {

    val resultsStream = liveData {
        emit(programRepository.getProgramResults())
    }
}