package tech.mycheckpoint.models.Calendars

import android.util.Log
import com.google.gson.GsonBuilder
import com.squareup.okhttp.*
import java.io.IOException


data class Calendar(val id: String, val summary: String, val bgColor: String, val fgColor: String, var selected: Boolean) {}


object Calendars {
    val MAX_QUERY_SIZE = 10
    val URL: String = "https://www.googleapis.com/calendar/v3/users/me/calendarList"

    var list = mutableListOf<Calendar>(
        Calendar("ilrl6rgsk2u0rai7rj1g4a1ppg@group.calendar.google.com","ACM Hack","#e380ff","#000000",true),

        Calendar( "vvhgvi2401lhjtsmo9q97ojjss@group.calendar.google.com",
        "Apartment 2020",
        "#ebe0ff",
        "#000000",
        true),

        Calendar( "4poet0hpt8dn5sba5fv2lanqo8@group.calendar.google.com",
        "Physics",
        "#bacbff",
        "#000000",
        true),


        Calendar( "bcp69klj4eks45757p0l0kt4do@group.calendar.google.com",
        "Exams",
        "#6c1e1e",
        "#ffffff",
        true),


        Calendar( "bp1ucssrgfo3vn5dc1lpuqq2po@group.calendar.google.com",
        "Exercise",
        "#b99aff",
        "#000000",
        true),


        Calendar( "rf4knnjeg7ulihf05lv341voq0@group.calendar.google.com",
        "Com Sci",
        "#357a9d",
        "#ffffff",
        true),

        Calendar( "ta8vpc4ni6vm2aqa4kgtqfc250@group.calendar.google.com",
        "Band",
        "#6f7291",
        "#ffffff",
        true),

        Calendar( "kj4jt9sbfro8lr9tuo812ondek@group.calendar.google.com",
        "Job",
        "#7e26fa",
        "#ffffff",
        true),


        Calendar( "nrnbb3c0rsgkgl1c7rn0l4u494@group.calendar.google.com",
        "Clubs",
        "#351fa4",
        "#ffffff",
        true),

        Calendar( "31j2a0mgs8cb1prak9ibbo08co@group.calendar.google.com",
        "Miles Juni",
        "#6b27c4",
        "#ffffff",
        true),


        Calendar( "p60k55iafv2q72h7gra0pep7co@group.calendar.google.com",
        "GE",
        "#946aa2",
        "#000000",
        true),

        Calendar( "ga6d5afa52ctrge2vlkck9uvcc@group.calendar.google.com",
        "Misc",
        "#731673",
        "#ffffff",
        true),

        Calendar( "mb54uo0ckhcfvllmgukrpmek7k@group.calendar.google.com",
        "ECE",
        "#9fc6e7",
        "#000000",
        true),

        Calendar( "38p5o2ekt1ro391679sftt6js8@group.calendar.google.com",
        "Office Hours",
        "#cd74e6",
        "#000000",
        true),

        Calendar( "mileswu13@gmail.com",
        "mileswu13@gmail.com",
        "#3955df",
        "#ffffff",
        true),

        Calendar( "mmb52162monlmk8b60drfnuie8@group.calendar.google.com",
        "Homework",
        "#9a9cff",
        "#000000",
        true),

        Calendar( "nprr8muh28hc4kfdmn4nas68hg@group.calendar.google.com",
        "Math",
        "#61b6cc",
        "#000000",
        true)
    )
    var pageToken: String? = null
    var syncToken: String? = null

    fun get(): MutableList<Calendar> {
        return list
    }

    fun get(position: Int): Calendar? {
        if (position < list.size) {
            return list.elementAt(position)
        }
        return null
    }

    fun set(calendars: MutableList<Calendar>) {
        list = calendars
    }

    fun add(calendar: Calendar) {
        list.add(calendar)
    }

    fun makeRequest() {
        println("Attempting to fetch data")
        var text: String
        val request = Request.Builder().url(URL).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                val body = response?.body()?.string()
                println(body)
                val gson = GsonBuilder().create()
                val results = gson.fromJson(body, Calendar::class.java)
                println("Calendars: ${results}")
            }

            override fun onFailure(request: Request?, e: IOException?) {
                println("Failed to execute request: ${e}")
            }
        })
    }
}
//        val connection = URL(url).openConnection() as HttpURLConnection
//        try {
//            connection.connect()
//            text = connection.inputStream.use {it.reader().use {reader -> reader.readText()}}
//        } finally {
//            connection.disconnect()
//        }
//        Log.i("response", text)

