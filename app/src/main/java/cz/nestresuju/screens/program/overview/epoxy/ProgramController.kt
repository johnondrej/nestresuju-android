package cz.nestresuju.screens.program.overview.epoxy

import com.airbnb.epoxy.EpoxyController

/**
 * Epoxy controller for programs overview.
 */
class ProgramController(
    private val onFirstProgramSelected: () -> Unit,
    private val onSecondProgramSelected: () -> Unit,
    private val onThirdProgramSelected: () -> Unit,
    private val onFourthProgramSelected: () -> Unit
) : EpoxyController() {

    override fun buildModels() {
        programCard {
            id("program-1")
            programName("Stanovování cílů")
            onProgramClicked(onFirstProgramSelected)
        }

        programCard {
            id("program-2")
            programName("Relaxace")
            onProgramClicked(onSecondProgramSelected)
        }

        programCard {
            id("program-3")
            programName("Řízení času")
            onProgramClicked(onThirdProgramSelected)
        }

        programCard {
            id("program-4")
            programName("Hledání smyslu a pozitiv")
            onProgramClicked(onFourthProgramSelected)
        }
    }
}