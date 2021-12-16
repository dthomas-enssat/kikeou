package fr.enssat.kikeou.thomas_bricaud.search

import androidx.lifecycle.ViewModel
import fr.enssat.kikeou.thomas_bricaud.database.PersonRepository
import fr.enssat.kikeou.thomas_bricaud.database.WeekRepository

class SearchModel(
    name: String,
    email: String,
    phone: String,
    week: String,
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
    var week        = String()
    var monday      = String()
    var tuesday     = String()
    var wednesday   = String()
    var thursday    = String()
    var friday      = String()
    var saturday    = String()

    lateinit var personRepository : PersonRepository
    lateinit var weekRepository: WeekRepository

    // init view
    init {
        this.name       = name
        this.email      = email
        this.phone      = phone
        this.week       = week
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