package fr.enssat.kikeou.thomas_bricaud.generate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GenerateQrCodeModel(name: String) : ViewModel() {
    var name = String()

    init {
        this.name = name
    }

    override fun onCleared() {
        super.onCleared()

    }
}