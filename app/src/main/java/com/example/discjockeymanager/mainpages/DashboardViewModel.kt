package com.example.discjockeymanager.mainpages

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.RequestType
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import org.json.JSONObject

/**
 * Data storage viewmodel for dashboard that persists between
 * page refreshes
 */

class DashboardViewModel : ViewModel() {
    val upcomingEventsCount = arrayOf(0,0,0,0)  //Values correspond to 7 days, 30 days, 90 days, 1 year
    val upcomingEvents = ArrayList<Entry>()     //Daily entries of upcoming events
    val newBookingsCount = arrayOf(0,0,0,0)     //Values correspond to 7 days, 30 days, 90 days, 1 year
    val newBookings = ArrayList<Entry>()        //Daily entries of past booked events
    var avgEventsCount = 0                      //Average number events per month
    val avgEvents = ArrayList<BarEntry>()       //Monthly number of booked events
    private val months = arrayOf(
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    )
    private var fetched = false

    //If not fetched, fetches all dashboard data from database, otherwise proceeds directly to callback
    fun fetchData(context: Context, callback: () -> Unit) {
        if (!fetched)
        {
            APIRequestHelper.jsonRequestWithAuth(context, RequestType.GET_ANALYTICS, JSONObject(), {
                setupAvgEvents(it)
                setupUpcomingEvents(it)
                setupNewBookings(it)
                callback()
                fetched = true
            })
        } else {
            callback()
        }
    }

    //Parses data entries of upcoming events into list
    private fun setupUpcomingEvents(jsonObj: JSONObject) {
        val upcomingEventsObj = jsonObj.getJSONObject("upcomingEvents")
        val data = upcomingEventsObj.getJSONArray("data")
        val numEvents = upcomingEventsObj.getJSONArray("numEvents")

        for (i in 0 until numEvents.length()) {
            upcomingEventsCount[i] = numEvents.getInt(i)
        }

        for (i in 0 until data.length()) {
            val str = data.getJSONObject(i).getString("x") //String representing date
            val e = Entry(i.toFloat(),data.getJSONObject(i).getInt("y").toFloat()) //Number of events
            e.data = str
            upcomingEvents.add(e)
        }
    }

    //Parses data entries of booked events into list
    private fun setupNewBookings(jsonObj: JSONObject) {
        val newBookingsObj = jsonObj.getJSONObject("newBookings")
        val data = newBookingsObj.getJSONArray("data")
        val numBookings = newBookingsObj.getJSONArray("numBookings")

        for (i in 0 until numBookings.length()) {
            newBookingsCount[i] = numBookings.getInt(i)
        }

        for (i in 0 until data.length()) {
            val str = data.getJSONObject(i).getString("x") //String representing date
            val e = Entry(i.toFloat(), data.getJSONObject(i).getInt("y").toFloat()) //Number of events
            e.data = str
            newBookings.add(e)
        }
    }

    //Parses data entries of monthly booked events into list
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