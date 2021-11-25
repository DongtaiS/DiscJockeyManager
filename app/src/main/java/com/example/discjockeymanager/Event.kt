package com.example.discjockeymanager

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


data class Event(
    val id: Int,
    val eventName: String,
    val client: String,
    val date: LocalDate,
    val bookedDate: LocalDate,
    val loadingTime: LocalTime,
    val startTime: LocalTime,
    val finishTime: LocalDateTime,
    val venue: String,
    val service: String,
    val staff: String,
    val system: String,
    val songs: String,
    val user_id: Int
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun parseEvent(e: JSONObject): Event {
            val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return Event(
                e.getInt("id"),
                e.getString("event_name"),
                e.getString("client"),
                LocalDate.parse(e.getString("event_date")),
                LocalDate.parse(e.getString("event_booked_date")),
                LocalTime.parse(e.getString("event_loading_time")),
                LocalTime.parse(e.getString("event_start_time")),
                LocalDateTime.parse(e.getString("event_finish_time"), dateTimeFormat),
                e.getString("venue"),
                e.getString("service"),
                e.getString("staff"),
                e.getString("system"),
                e.getString("songs"),
                e.getInt("user_id")
            )
        }
    }
}