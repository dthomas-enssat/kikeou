package fr.enssat.kikeou.thomas_bricaud

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.enssat.kikeou.thomas_bricaud.database.PersonRepository
import fr.enssat.kikeou.thomas_bricaud.database.PersonRoomDatabase
import fr.enssat.kikeou.thomas_bricaud.database.WeekRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class MainActivity : AppCompatActivity() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private lateinit var database : PersonRoomDatabase
    lateinit var weekRepository : WeekRepository
    lateinit var personRepository : PersonRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = PersonRoomDatabase.getDatabase(this, applicationScope)
        weekRepository = WeekRepository(database.weekDao())
        personRepository = PersonRepository(database.personDao())

        // initialize
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)

        bottomNavigationView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.search, R.id.generateQrCode, R.id.scanQrCode))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}