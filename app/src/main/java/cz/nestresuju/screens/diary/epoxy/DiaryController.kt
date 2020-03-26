package cz.nestresuju.screens.diary.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.common.extensions.adapterProperty
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressLevel

/**
 * Epoxy controller for diary.
 */
class DiaryController(
    private val onStressLevelSelected: (StressLevel) -> Unit,
    private val onAnswerChanged: (String) -> Unit,
    private val onInputConfirmed: () -> Unit,
    private val onStressLevelEditClicked: (DiaryEntry.StressLevelEntry) -> Unit,
    private val onNoteEditClicked: (DiaryEntry.NoteEntry) -> Unit,
    private val onNoteDeleteClicked: (DiaryEntry.NoteEntry) -> Unit
) : EpoxyController() {

    var answer: String? by adapterProperty(null)
    var input: Pair<StressLevel, String>? by adapterProperty(null)

    override fun buildModels() {
        diaryInputLarge {
            id("input")
            onStressLevelSelected(onStressLevelSelected)
            onInputConfirmed(onInputConfirmed)
            onAnswerChanged(onAnswerChanged)
            answer(answer)
            input(input)
        }
    }
}