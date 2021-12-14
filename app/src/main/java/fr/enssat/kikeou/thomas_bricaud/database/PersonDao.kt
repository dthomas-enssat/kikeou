package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM person_table")
    fun getPersons(): Flow<List<Person>>

    @Query("SELECT * FROM person_table WHERE name=:name")
    fun getPerson(name: String) : List<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(person: Person)

    @Query("DELETE FROM person_table")
    fun deleteAll()
}