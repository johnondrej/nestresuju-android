package cz.nestresuju.screens.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

class AboutAppViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "O aplikaci"
    }
    val text: LiveData<String> = _text
}