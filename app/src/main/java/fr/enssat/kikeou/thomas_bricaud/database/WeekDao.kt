package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekDao {
    @Query("SELECT * FROM week_table")
    fun getWeeks(): Flow<List<Week>>

    @Query("SELECT * FROM week_table WHERE num=:num AND name=:name")
    fun getWeek(num: Int, name: String) : List<Week>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeek(week: Week);
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(week: Week)

    @Query("DELETE FROM week_table")
    fun deleteAll()
}