package fr.enssat.kikeou.thomas_bricaud.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

class Location(var day:Int, var place:String)


@Entity(tableName = "week_table", foreignKeys = [
    ForeignKey(entity = Person::class,
        parentColumns = ["name"],
        childColumns = ["name"],
        onDelete = CASCADE
        )
])
data class Week(@ColumnInfo(name = "num") var num:Int,
                @ColumnInfo(name = "name") var name:String,
                @ColumnInfo(name = "loc") var loc:Array<Location>
                ){
    @PrimaryKey(autoGenerate = true)
    private var id:Int =0
        fun getId():Int {
            return id
        }
        fun setId(value:Int) {
            id = value
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Week

        if (num != other.num) return false
        if (name != other.name) return false
        if (!loc.contentEquals(other.loc)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = num
        result = 31 * result + name.hashCode()
        result = 31 * result + loc.contentHashCode()
        result = 31 * result + id
        return result
    }
}
