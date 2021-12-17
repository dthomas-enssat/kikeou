package fr.enssat.kikeou.thomas_bricaud.database

class PersonRepository (private val personDao: PersonDao){

    fun getPersons(): List<Person> {
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

    fun delete(name: String) {
        return personDao.delete(name)
    }

}