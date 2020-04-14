package cz.nestresuju.screens.program.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.repositories.ProgramThirdRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Common ViewModel for goal questions in third program.
 */
class ProgramThirdGoalQuestionViewModel(
    private val progress: Int,
    private val programRepository: ProgramThirdRepository
) : BaseViewModel() {

    private val _answerLiveData = MutableLiveData("")
    val answerStream: LiveData<String>
        get() = _answerLiveData

    init {
        viewModelScope.launch {
            val initialAnswer = initAnswer()
            if (answerStream.value.isNullOrBlank()) {
                _answerLiveData.value = initialAnswer
            }
            programRepository.updateProgramResults { currentResults -> currentResults.copy(progress = progress) }
        }
    }

    fun onAnswerChanged(answer: String) {
        _answerLiveData.value = answer
        viewModelScope.launch(Dispatchers.IO) {
            // debounce
            delay(300)
            if (answerStream.value == answer) {
                programRepository.updateProgramResults { currentResults -> updateResults(currentResults, answer) }
            }
        }
    }

    private fun updateResults(currentResults: ProgramThirdResults, answer: String): ProgramThirdResults {
        return when (progress) {
            5 -> currentResults.copy(target = answer)
            6 -> currentResults.copy(completion = answer)
            8 -> currentResults.copy(reason = answer)
            9 -> currentResults.copy(deadline = answer)
            else -> throw IllegalArgumentException("Invalid progress value $progress")
        }
    }

    private suspend fun initAnswer(): String {
        val results = programRepository.getProgramResults()
        return when (progress) {
            5 -> results.target
            6 -> results.completion
            8 -> results.reason
            9 -> results.deadline
            else -> throw IllegalArgumentException("Invalid progress value $progress")
        }
    }
}