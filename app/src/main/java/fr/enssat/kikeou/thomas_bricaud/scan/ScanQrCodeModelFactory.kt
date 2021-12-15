package fr.enssat.kikeou.thomas_bricaud.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ScanQrCodeModelFactory (
    private val name: String,
    private val email: String,
    private val phone: String,
    private val monday: String,
    private val tuesday: String,
    private val wednesday: String,
    private val thursday: String,
    private val friday: String,
    private val saturday: String,
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanQrCodeModel::class.java)) {
            return ScanQrCodeModel(
                name, email, phone, monday, tuesday, wednesday, thursday, friday, saturday
            ) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}