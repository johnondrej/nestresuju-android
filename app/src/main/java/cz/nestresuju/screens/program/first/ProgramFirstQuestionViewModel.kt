package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

/**
 * Common ViewModel for questions in first program.
 */
class ProgramFirstQuestionViewModel : BaseViewModel() {

    private val _answerLiveData = MutableLiveData("")
    val answerStream: LiveData<String>
        get() = _answerLiveData

    fun onAnswerChanged(answer: String) {
        _answerLiveData.value = answer
    }
}