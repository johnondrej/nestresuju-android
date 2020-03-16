package cz.nestresuju.screens.program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

class ProgramViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Program"
    }
    val text: LiveData<String> = _text
}