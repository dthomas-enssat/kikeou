/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.enssat.kikeou.thomas_bricaud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Person::class, Week::class], version = 1)
abstract class PersonRoomDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun weekDao(): WeekDao

    companion object {
        @Volatile
        private var INSTANCE: PersonRoomDatabase? = null


        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PersonRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PersonRoomDatabase::class.java,
                    "person_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.personDao(), database.weekDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        fun populateDatabase(personDao: PersonDao, weekDao: WeekDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            personDao.deleteAll()
            weekDao.deleteAll()

            var person = Person("David", null, Contact("dthomas@enssat.fr", null, null))
            personDao.insert(person)
            person = Person("Dorian!", null, Contact("dbricaud@enssat.fr", null, null))
            personDao.insert(person)

            val day1 = "here"
            val day2 = "there"
            val day3 = "home"
            val day4 = "office"
            val day5 = "somewhere"


            var week = Week(1, "David", day1, day2, day3, day4, day5)
            weekDao.insert(week)

            week = Week(1, "Dorian", day1, day2, day3, day4, day5)
            weekDao.insert(week)
        }
    }
}
