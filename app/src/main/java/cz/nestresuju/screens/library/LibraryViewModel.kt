package cz.nestresuju.screens.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

class LibraryViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Knihovna"
    }
    val text: LiveData<String> = _text
}