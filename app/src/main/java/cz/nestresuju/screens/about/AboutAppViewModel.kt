package cz.nestresuju.screens.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutAppViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "O aplikaci"
    }
    val text: LiveData<String> = _text
}