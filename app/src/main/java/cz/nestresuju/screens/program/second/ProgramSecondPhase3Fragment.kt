package cz.nestresuju.screens.program.second

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R

/**
 * Fragment with third part of second program.
 */
class ProgramSecondPhase3Fragment : ProgramSecondBaseInstructionsFragment() {

    override val instructionsRes = R.string.program_2_instructions_3
    override val phaseProgress = 3

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_2_3_to_fragment_program_2_4)
    }
}