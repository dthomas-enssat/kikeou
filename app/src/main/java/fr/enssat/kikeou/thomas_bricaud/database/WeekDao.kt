package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekDao {
    @Query("SELECT * FROM week_table")
    fun getWeeks(): Flow<List<Week>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(week: Week)

    @Query("DELETE FROM week_table")
    fun deleteAll()
}