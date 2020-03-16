package cz.nestresuju.screens.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

class DiaryViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Deníček"
    }
    val text: LiveData<String> = _text
}