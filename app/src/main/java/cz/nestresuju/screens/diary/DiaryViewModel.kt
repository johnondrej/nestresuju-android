package cz.nestresuju.screens.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiaryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Deníček"
    }
    val text: LiveData<String> = _text
}