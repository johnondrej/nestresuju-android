package cz.nestresuju.screens.program.fourth

import androidx.lifecycle.liveData
import cz.nestresuju.R
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthResults
import cz.nestresuju.model.repositories.ProgramFourthRepository
import cz.nestresuju.screens.base.BaseViewModel
import cz.nestresuju.screens.program.fourth.ProgramFourthConstants.PRESENT_EVALUATION_THRESHOLD
import cz.nestresuju.screens.program.fourth.ProgramFourthConstants.SEARCHING_EVALUATION_THRESHOLD

/**
 * ViewModel for summary/evaluation of program 4.
 */
class ProgramFourthSummaryViewModel(private val programRepository: ProgramFourthRepository) : BaseViewModel() {

    val evaluationStream = liveData {
        emit(getEvaluationTextRes(programRepository.getProgramResults()))
    }

    private fun getEvaluationTextRes(programResults: ProgramFourthResults): Int {
        return when {
            programResults.presentScore >= PRESENT_EVALUATION_THRESHOLD -> when {
                programResults.searchingScore >= SEARCHING_EVALUATION_THRESHOLD -> R.string.program_4_evaluation_above_above
                else -> R.string.program_4_evaluation_above_below
            }
            else -> when {
                programResults.searchingScore >= SEARCHING_EVALUATION_THRESHOLD -> R.string.program_4_evaluation_below_above
                else -> R.string.program_4_evaluation_below_below
            }
        }
    }
}