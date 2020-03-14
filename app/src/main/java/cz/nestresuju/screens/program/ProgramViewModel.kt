package cz.nestresuju.screens.program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgramViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Program"
    }
    val text: LiveData<String> = _text
}