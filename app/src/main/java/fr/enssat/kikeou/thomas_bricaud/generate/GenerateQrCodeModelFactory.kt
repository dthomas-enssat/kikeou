package fr.enssat.kikeou.thomas_bricaud.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GenerateQrCodeModelFactory (private val name: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenerateQrCodeModel::class.java)) {
            return GenerateQrCodeModel(name) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}