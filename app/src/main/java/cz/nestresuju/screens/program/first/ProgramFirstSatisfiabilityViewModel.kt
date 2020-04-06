package cz.nestresuju.screens.program.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for phase
 */
class ProgramFirstSatisfiabilityViewModel : BaseViewModel() {

    companion object {

        const val MIN_SATISFIABILITY_ALLOWED = 6
    }

    private val _scaleLiveData = MutableLiveData(1)
    val scaleStream: LiveData<Int>
        get() = _scaleLiveData

    fun onScaleChanged(scale: Int) {
        _scaleLiveData.value = scale
    }
}