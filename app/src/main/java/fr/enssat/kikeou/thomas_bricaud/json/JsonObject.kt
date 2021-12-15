package fr.enssat.kikeou.thomas_bricaud.json

import fr.enssat.kikeou.thomas_bricaud.database.Person
import fr.enssat.kikeou.thomas_bricaud.database.Week
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.enssat.kikeou.thomas_bricaud.database.Contact

@JsonClass(generateAdapter = true)
class JsonObject() {
    @field:Json(name = "id")
    var id : Int? = null
    @field:Json(name = "name")
    var name = ""
    @field:Json(name = "photo")
    var photo : String?= null
    @field:Json(name = "contact")
    var contact = Contact("", null, null)
    @field:Json(name = "week")
    var num : Int = 0
    @field:Json(name = "loc")
    var loc = arrayOf(
        Day(1, ""),
        Day(2, ""),
        Day(3, ""),
        Day(4, ""),
        Day(5, ""),
        Day(6, "")
    )

    constructor(person : Person, week: Week) : this() {
        id = week.getId()
        name = person.name
        photo = person.photo
        contact = person.contact
        num = week.num
        loc = arrayOf(
            Day(1, week.day1),
            Day(2, week.day2),
            Day(3, week.day3),
            Day(4, week.day4),
            Day(5, week.day5),
            Day(6, week.day6),
        )
    }

    fun getPerson() : Person {
        return Person(name, photo, contact)
    }

    fun getWeek() : Week {
        return Week(num, name, loc[0].value, loc[1].value, loc[2].value,
            loc[3].value, loc[4].value, loc[5].value)
    }
}

@JsonClass(generateAdapter = true)
class Day(var day: Int, var value: String)