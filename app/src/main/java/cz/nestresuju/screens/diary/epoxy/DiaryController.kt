package cz.nestresuju.screens.diary.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.common.extensions.adapterProperty
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressLevel
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

    var showSmallInput by adapterProperty(false)
    var answer: String? by adapterProperty(null)
    var input: Pair<StressLevel, String>? by adapterProperty(null)
    var entries: List<DiaryEntry>? by adapterProperty(null)

    private var lastDay: LocalDate? = null

    override fun buildModels() {
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
                    val entryDate = entry.createdAt.toLocalDate()
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