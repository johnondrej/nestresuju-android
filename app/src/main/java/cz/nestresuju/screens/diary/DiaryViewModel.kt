package cz.nestresuju.screens.diary

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.R
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.model.entities.domain.diary.StressQuestion
import cz.nestresuju.model.repositories.DiaryRepository
import cz.nestresuju.screens.base.BaseViewModel
import cz.nestresuju.screens.diary.errors.EmptyAnswerException

class DiaryViewModel(
    private val applicationContext: Context,
    private val diaryRepository: DiaryRepository
) : BaseViewModel() {

    private val _inputLiveData = MutableLiveData<DiaryChoiceInput>()
    val inputStream: LiveData<DiaryChoiceInput> = _inputLiveData

    private val _entriesLiveData = MutableLiveData<List<DiaryEntry>>()
    val entriesStream: LiveData<List<DiaryEntry>> = _entriesLiveData

    private lateinit var questionsGenerator: QuestionGenerator

    val clearAnswerEvent = LiveEvent<Unit>()
    var answer: String? = null

    init {
        fetchDiaryEntries()
    }

    fun fetchDiaryEntries() {
        viewModelScope.launchWithErrorHandling {
            val diaryEntries = diaryRepository.getDiaryEntries()
            val stressQuestions = diaryRepository.getStressQuestions()

            questionsGenerator = QuestionGenerator(applicationContext, stressQuestions)
            _entriesLiveData.value = diaryEntries
        }
    }

    fun onStressLevelSelected(stressLevel: StressLevel?) {
        _inputLiveData.value = stressLevel?.let { DiaryChoiceInput(it, questionsGenerator.generate(it)) }
        clearAnswerEvent.value = Unit
        answer = null
    }

    fun onEditEntry(entryId: Long, modifiedText: String) {
        viewModelScope.launchWithErrorHandling {
            diaryRepository.editEntry(entryId, modifiedText)
        }
    }

    fun onDeleteEntry(entry: DiaryEntry) {
        viewModelScope.launchWithErrorHandling {
            diaryRepository.deleteEntry(entry)
        }
    }

    fun onAnswerConfirmed() {
        answer?.takeIf { it.isNotBlank() }?.let { confirmedAnswer ->
            val diaryInput = _inputLiveData.value!!

            viewModelScope.launchWithErrorHandling {
                when (diaryInput.stressLevel) {
                    StressLevel.NONE -> {
                        diaryRepository.createNoteEntry(confirmedAnswer)
                    }
                    else -> {
                        diaryRepository.createStressLevelEntry(
                            stressLevel = diaryInput.stressLevel,
                            question = diaryInput.question,
                            answer = confirmedAnswer
                        )
                    }
                }
                onStressLevelSelected(null)
            }
            confirmedAnswer
        } ?: run {
            errorStream.value = EmptyAnswerException()
        }
    }

    private class QuestionGenerator(
        private val applicationContext: Context,
        private val questions: List<StressQuestion>
    ) {

        private val questionsMap = mutableMapOf<StressLevel, StressQuestion>()

        fun generate(stressLevel: StressLevel): StressQuestion {
            return when (stressLevel) {
                StressLevel.NONE -> StressQuestion(0, StressLevel.NONE, applicationContext.getString(R.string.diary_note_title))
                else -> questionsMap[stressLevel] ?: questions.filter { it.stressLevel == stressLevel }.random().also {
                    questionsMap[stressLevel] = it
                }
            }
        }
    }
}