package fr.enssat.kikeou.thomas_bricaud.generate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GenerateQrCodeModel(name: String) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    init {
        _name.value = name
    }
}