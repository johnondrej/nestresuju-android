package cz.nestresuju.screens.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.screens.base.BaseViewModel

class DiaryViewModel : BaseViewModel() {

    private val _stressLevelLiveData = MutableLiveData<StressLevel>()
    val stressLevelStream: LiveData<StressLevel> = _stressLevelLiveData

    val clearAnswerEvent = LiveEvent<Unit>()

    fun onStressLevelSelected(stressLevel: StressLevel) {
        _stressLevelLiveData.value = stressLevel
        clearAnswerEvent.value = Unit
    }

    fun onAnswerConfirmed(answer: String) {
        // TODO
    }
}