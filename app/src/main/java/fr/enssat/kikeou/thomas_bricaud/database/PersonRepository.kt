package fr.enssat.kikeou.thomas_bricaud.database

import kotlinx.coroutines.flow.Flow

class PersonRepository (private val personDao: PersonDao){

    fun getPersons(): Flow<List<Person>> {
        return personDao.getPersons()
    }

    fun getPerson(name: String) : List<Person> {
        return personDao.getPerson(name)
    }

    fun insert(person: Person) {
        return personDao.insert(person)
    }

    fun deleteAll() {
        return personDao.deleteAll()
    }

}