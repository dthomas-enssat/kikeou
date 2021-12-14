package fr.enssat.kikeou.thomas_bricaud.database

import kotlinx.coroutines.flow.Flow

class WeekRepository(private val weekDao: WeekDao) {

    fun getWeeks(): Flow<List<Week>> {
        return weekDao.getWeeks()
    }

    fun getWeek(num: Int, name: String) : List<Week> {
        return weekDao.getWeek(num, name)
    }

    fun updateWeek(week: Week) {
        return weekDao.updateWeek(week)
    }

    fun insert(week: Week){
        return weekDao.insert(week)
    }

    fun deleteAll() {
        return weekDao.deleteAll()
    }
}