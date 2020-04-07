package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Common ViewModel for questions in first program.
 */
class ProgramFirstQuestionViewModel(
    private val progress: Int,
    private val programRepository: ProgramFirstRepository
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

    private fun updateResults(currentResults: ProgramFirstResults, answer: String): ProgramFirstResults {
        return when (progress) {
            1 -> currentResults.copy(target = answer)
            2 -> currentResults.copy(completion = answer)
            4 -> currentResults.copy(reason = answer)
            5 -> currentResults.copy(deadline = answer)
            else -> throw IllegalArgumentException("Invalid progress value $progress")
        }
    }

    private suspend fun initAnswer(): String {
        val results = programRepository.getProgramResults()
        return when (progress) {
            1 -> results.target
            2 -> results.completion
            4 -> results.reason
            5 -> results.deadline
            else -> throw IllegalArgumentException("Invalid progress value $progress")
        }
    }
}