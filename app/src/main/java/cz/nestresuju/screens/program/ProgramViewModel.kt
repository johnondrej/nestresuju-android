package cz.nestresuju.screens.program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.launch

class ProgramViewModel(
    private val programFirstRepository: ProgramFirstRepository
) : BaseViewModel() {

    private val _progressLiveData = MutableLiveData<ProgramsProgress>()
    val progressStream: LiveData<ProgramsProgress>
        get() = _progressLiveData

    private val _text = MutableLiveData<String>().apply {
        value = "Program"
    }
    val text: LiveData<String> = _text

    init {
        viewModelScope.launch {
            val programFirstProgress = programFirstRepository.getProgramResults().progress

            _progressLiveData.value = ProgramsProgress(
                programFirstProgress
            )
        }
    }

    data class ProgramsProgress(
        val programFirstProgress: Int
    )
}