package com.example.discjockeymanager

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import org.json.JSONObject

class DashboardViewModel : ViewModel() {
    val upcomingEventsCount = arrayOf(0,0,0,0)
    val upcomingEvents = ArrayList<Entry>()
    val newBookingsCount = arrayOf(0,0,0,0)
    val newBookings = ArrayList<Entry>()
    var avgEventsCount = 0
    val avgEvents = ArrayList<BarEntry>()
    private val months = arrayOf(
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    )
    private var fetched = false

    fun fetchData(context: Context, callback: () -> Unit) {
        if (!fetched)
        {
            APIRequestHelper.jsonRequestWithAuth(context, RequestType.GET_ANALYTICS, JSONObject(), {
                setupAvgEvents(it)
                setupUpcomingEvents(it)
                setupNewBookings(it)
                callback()
                fetched = true
            } )
        } else {
            callback()
        }
    }
    private fun setupUpcomingEvents(jsonObj: JSONObject) {
        val upcomingEventsObj = jsonObj.getJSONObject("upcomingEvents")
        val data = upcomingEventsObj.getJSONArray("data")
        val numEvents = upcomingEventsObj.getJSONArray("numEvents")
        for (i in 0 until numEvents.length()) {
            upcomingEventsCount[i] = numEvents.getInt(i)
        }
        for (i in 0 until data.length()) {
            val str = data.getJSONObject(i).getString("x")
            val e = Entry(i.toFloat(),data.getJSONObject(i).getInt("y").toFloat())
            e.data = str
            upcomingEvents.add(e)
        }
    }
    private fun setupNewBookings(jsonObj: JSONObject) {
        val newBookingsObj = jsonObj.getJSONObject("newBookings")
        val data = newBookingsObj.getJSONArray("data")
        val numBookings = newBookingsObj.getJSONArray("numBookings")
        for (i in 0 until numBookings.length()) {
            newBookingsCount[i] = numBookings.getInt(i)
        }
        for (i in 0 until data.length()) {
            val str = data.getJSONObject(i).getString("x")
            val e = Entry(i.toFloat(), data.getJSONObject(i).getInt("y").toFloat())
            e.data = str
            newBookings.add(e)
        }
    }
    private fun setupAvgEvents(jsonObj : JSONObject) {
        val avgEventsObj = jsonObj.getJSONObject("avgEvents")
        avgEventsCount = avgEventsObj.getInt("avgEvents")
        val data = avgEventsObj.getJSONArray("series").getJSONObject(0).getJSONArray("data")
        for (i in 0 until data.length()) {
            val b = BarEntry(i.toFloat(), data.getInt(i).toFloat())
            b.data = months[i]
            avgEvents.add(b)
        }
    }
}