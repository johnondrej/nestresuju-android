package cz.nestresuju.screens.program.second

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R

/**
 * Fragment with first part of second program.
 */
class ProgramSecondPhase1Fragment : ProgramSecondBaseInstructionsFragment() {

    override val instructionsRes = R.string.program_2_instructions_1
    override val phaseProgress = 1

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_2_1_to_fragment_program_2_2)
    }
}