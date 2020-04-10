package cz.nestresuju.screens.program.second

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R

/**
 * Fragment with fourth part of second program.
 */
class ProgramSecondPhase4Fragment : ProgramSecondBaseInstructionsFragment() {

    override val instructionsRes = R.string.program_2_instructions_4
    override val phaseProgress = 4

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_2_4_to_fragment_program_2_5)
    }
}