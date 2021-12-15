package fr.enssat.kikeou.thomas_bricaud.scan

import androidx.lifecycle.ViewModel
import fr.enssat.kikeou.thomas_bricaud.database.PersonRepository
import fr.enssat.kikeou.thomas_bricaud.database.WeekRepository

class ScanQrCodeModel(
    name: String,
    email: String,
    phone: String,
    monday: String,
    tuesday: String,
    wednesday: String,
    thursday: String,
    friday: String,
    saturday: String,
    personRepository: PersonRepository,
    weekRepository: WeekRepository
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

    // database
    var personRepository : PersonRepository
    var weekRepository : WeekRepository

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
        this.personRepository = personRepository
        this.weekRepository = weekRepository
    }

    override fun onCleared() {
        super.onCleared()
    }
}