package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for last screen of program 1.
 */
class ProgramFirstOverviewViewModel : BaseViewModel() {

    private val _summaryLiveData = MutableLiveData("")
    val summaryStream: LiveData<String>
        get() = _summaryLiveData

    fun onSummaryChanged(summary: String) {
        _summaryLiveData.value = summary
    }
}