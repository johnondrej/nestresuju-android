package cz.nestresuju.screens.program.second

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R

/**
 * Fragment with fifth part of second program.
 */
class ProgramSecondPhase5Fragment : ProgramSecondBaseInstructionsFragment() {

    override val instructionsRes = R.string.program_2_instructions_5
    override val phaseProgress = 5

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_2_5_to_fragment_program_2_6)
    }
}