package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(@PrimaryKey @ColumnInfo(name = "person") val person: String) {
}