package fr.enssat.kikeou.thomas_bricaud.search

import androidx.lifecycle.ViewModel

class SearchModel(
    name: String,
    email: String,
    phone: String,
    monday: String,
    tuesday: String,
    wednesday: String,
    thursday: String,
    friday: String,
    saturday: String,
) : ViewModel() {
    // personnal information
    var name    = String()
    var email   = String()
    var phone   = String()

    // actual week information
    var monday      = String()
    var tuesday     = String()
    var wednesday   = String()
    var thursday    = String()
    var friday      = String()
    var saturday    = String()

    // init view
    init {
        this.name       = name
        this.email      = email
        this.phone      = phone
        this.monday     = monday
        this.tuesday    = tuesday
        this.wednesday  = wednesday
        this.thursday   = thursday
        this.friday     = friday
        this.saturday   = saturday
    }

    override fun onCleared() {
        super.onCleared()
    }
}