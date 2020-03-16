package cz.nestresuju.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.nestresuju.screens.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Nástěnka"
    }
    val text: LiveData<String> = _text
}