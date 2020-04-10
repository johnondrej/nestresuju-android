package cz.nestresuju.screens.program.second

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R

/**
 * Fragment with second part of second program.
 */
class ProgramSecondPhase2Fragment : ProgramSecondBaseInstructionsFragment() {

    override val instructionsRes = R.string.program_2_instructions_2
    override val phaseProgress = 2

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_2_2_to_fragment_program_2_3)
    }
}