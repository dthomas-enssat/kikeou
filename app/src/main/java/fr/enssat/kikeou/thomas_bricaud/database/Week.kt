package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "week_table")
data class Week(@ColumnInfo(name = "num") var num:Int,
                @ColumnInfo(name = "name") var name:String,
                @ColumnInfo(name = "day1") var day1:String,
                @ColumnInfo(name = "day2") var day2:String,
                @ColumnInfo(name = "day3") var day3:String,
                @ColumnInfo(name = "day4") var day4:String,
                @ColumnInfo(name = "day5") var day5:String,
                @ColumnInfo(name = "day6") var day6:String
                ){
    @PrimaryKey(autoGenerate = true)
    private var id:Int =0
        fun getId():Int {
            return id
        }
        fun setId(value:Int) {
            id = value
        }
}
