package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

class Contact(var mail:String, var tel:String?, var fb:String?)

@Entity(tableName = "person_table")
data class Person(@PrimaryKey() @ColumnInfo(name = "name") var name: String,
                  @ColumnInfo(name = "photo") var photo: String?,
                  @Embedded
                  var contact: Contact
                  ) {
}