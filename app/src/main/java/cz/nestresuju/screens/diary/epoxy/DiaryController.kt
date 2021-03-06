package cz.nestresuju.screens.diary.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.common.extensions.controllerProperty
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.screens.diary.DiaryChoiceInput
import org.threeten.bp.LocalDate

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

    var isInitialized by controllerProperty(false)
    var showSmallInput by controllerProperty(false)
    var answer: String? by controllerProperty(null)
    var input: DiaryChoiceInput? by controllerProperty(null)
    var entries: List<DiaryEntry>? by controllerProperty(null)

    private var lastDay: LocalDate? = null

    override fun buildModels() {
        lastDay = null
        if (!isInitialized) {
            return
        }

        entries?.let { diaryEntries ->
            if (showSmallInput) {
                diaryInputSmall {
                    id("input")
                    onStressLevelSelected(onStressLevelSelected)
                    onInputConfirmed(onInputConfirmed)
                    onAnswerChanged(onAnswerChanged)
                    answer(answer)
                    input(input)
                }
            } else {
                diaryInputLarge {
                    id("input")
                    onStressLevelSelected(onStressLevelSelected)
                    onInputConfirmed(onInputConfirmed)
                    onAnswerChanged(onAnswerChanged)
                    answer(answer)
                    input(input)
                }
            }

            if (diaryEntries.isNotEmpty()) {
                diaryEntries.forEach { entry ->
                    val entryDate = entry.dateCreated.toLocalDate()
                    if (entryDate != lastDay) {
                        diaryDayHeader {
                            id("day-${entryDate.toEpochDay()}")
                            date(entryDate)
                        }

                        lastDay = entryDate
                    }

                    when (entry) {
                        is DiaryEntry.StressLevelEntry -> {
                            diaryStressLevel {
                                id("stresslevel-${entry.id}")
                                stressLevelEntry(entry)
                                onEditClicked(onStressLevelEditClicked)
                            }
                        }
                        is DiaryEntry.NoteEntry -> {
                            diaryNote {
                                id("note-${entry.id}")
                                noteEntry(entry)
                                onEditClicked(onNoteEditClicked)
                                onDeleteClicked(onNoteDeleteClicked)
                            }
                        }
                    }
                }
            } else {
                diaryEmpty {
                    id("empty")
                }
            }
        }
    }
}