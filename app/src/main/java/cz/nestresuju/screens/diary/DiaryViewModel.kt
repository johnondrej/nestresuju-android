package cz.nestresuju.screens.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressLevel
import cz.nestresuju.model.repositories.DiaryRepository
import cz.nestresuju.screens.base.BaseViewModel
import cz.nestresuju.screens.diary.errors.EmptyAnswerException

class DiaryViewModel(private val diaryRepository: DiaryRepository) : BaseViewModel() {

    private val _stressLevelLiveData = MutableLiveData<StressLevel>()
    val stressLevelStream: LiveData<StressLevel> = _stressLevelLiveData

    private val _entriesLiveData = MutableLiveData<List<DiaryEntry>>()
    val entriesStream: LiveData<List<DiaryEntry>> = _entriesLiveData

    val clearAnswerEvent = LiveEvent<Unit>()
    var answer: String? = null

    init {
        fetchDiaryEntries()
    }

    fun fetchDiaryEntries() {
        viewModelScope.launchWithErrorHandling {
            val moodQuestions = diaryRepository.fetchMoodQuestions()
            val diaryEntries = diaryRepository.fetchDiaryEntries(moodQuestions)

            _entriesLiveData.value = diaryEntries
        }
    }

    fun onStressLevelSelected(stressLevel: StressLevel) {
        _stressLevelLiveData.value = stressLevel
        clearAnswerEvent.value = Unit
        answer = null
    }

    fun onAnswerConfirmed() {
        if (!answer.isNullOrBlank()) {
            // TODO
        } else {
            errorStream.value = EmptyAnswerException()
        }
    }
}