package fr.enssat.kikeou.thomas_bricaud

import android.app.Application
import android.content.Context
import fr.enssat.kikeou.thomas_bricaud.database.PersonRepository
import fr.enssat.kikeou.thomas_bricaud.database.PersonRoomDatabase
import fr.enssat.kikeou.thomas_bricaud.database.WeekRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class KikeouApplication : Application(){

    private lateinit var database : PersonRoomDatabase
    lateinit var weekRepository : WeekRepository
    lateinit var personRepository : PersonRepository

    override fun onCreate() {
        super.onCreate()
        database = PersonRoomDatabase.getDatabase(this, CoroutineScope(SupervisorJob()))
        weekRepository = WeekRepository(database.weekDao())
        personRepository = PersonRepository(database.personDao())
    }
}