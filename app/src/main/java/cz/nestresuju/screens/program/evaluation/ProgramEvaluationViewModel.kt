package cz.nestresuju.screens.program.evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.R
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.model.entities.domain.program.evaluation.ProgramEvaluation
import cz.nestresuju.model.repositories.ProgramEvaluationRepository
import cz.nestresuju.model.repositories.ProgramOverviewRepository
import cz.nestresuju.screens.base.BaseViewModel
import org.threeten.bp.ZonedDateTime

/**
 * ViewModel for program evaluation.
 */
class ProgramEvaluationViewModel(
    private val programId: ProgramId,
    private val programEvaluationRepository: ProgramEvaluationRepository,
    private val programOverviewRepository: ProgramOverviewRepository
) : BaseViewModel() {

    private val _inputValidLiveData = MutableLiveData(false)
    val inputValidStream: LiveData<Boolean>
        get() = _inputValidLiveData.distinctUntilChanged()

    val completionEvent = LiveEvent<Int>()

    var questionFirstAnswer = 0
        set(value) {
            field = value
            updateInputValidity()
        }
    var questionSecondAnswer = 0
        set(value) {
            field = value
            updateInputValidity()
        }
    var questionThirdAnswer = ""
        set(value) {
            field = value
            updateInputValidity()
        }

    fun submitEvaluation() {
        if (!isInputValid()) {
            return
        }

        val evaluation = ProgramEvaluation(
            programId = programId.txtId,
            fulfillment = questionFirstAnswer,
            difficulty = questionSecondAnswer,
            message = questionThirdAnswer,
            dateCreated = ZonedDateTime.now()
        )

        viewModelScope.launchWithErrorHandling {
            programEvaluationRepository.submitProgramEvaluation(evaluation)
            programOverviewRepository.updateProgramEvaluationState(programId)

            val navigation = when (programId) {
                ProgramId.PROGRAM_FIRST_ID -> R.id.action_fragment_program_evaluation_to_fragment_program_2_intro_1
                ProgramId.PROGRAM_SECOND_ID -> R.id.action_fragment_program_evaluation_to_fragment_program_3_intro
                ProgramId.PROGRAM_THIRD_ID -> R.id.action_fragment_program_evaluation_to_fragment_program_4_1
                ProgramId.PROGRAM_FOURTH_ID -> R.id.action_fragment_program_evaluation_to_fragment_program_overview
            }
            completionEvent.value = navigation
        }
    }

    private fun updateInputValidity() {
        _inputValidLiveData.value = isInputValid()
    }

    private fun isInputValid() = questionFirstAnswer in 1..3 && questionSecondAnswer in 1..4 && !questionThirdAnswer.isBlank()
}