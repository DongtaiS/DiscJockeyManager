package com.example.discjockeymanager.mainpages


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.objects.Event
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.CalendarEventItemBinding
import com.example.discjockeymanager.databinding.FragmentCalendarBinding
import org.json.JSONObject
import java.util.*

/**
 * Fragment page for event calendar, WIP
 */

class CalendarFragment : Fragment() {
    //List of events from database
    private val eventList = ArrayList<Event>()

    //List of calendar EventDays with date and icon
    private val calendarEvents = ArrayList<EventDay>()

    //Map of dates to events on that date
    private val dayToEvent = HashMap<Calendar, ArrayList<Event>>()
    private lateinit var binding: FragmentCalendarBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentCalendarBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_EVENTS,
            JSONObject(),
            {
                val eventArr = it.getJSONArray("events")

                for (i in 0 until eventArr.length()) {
                    //Retrieves event objects from JSONArray
                    val event = Event.parseEvent(eventArr.getJSONObject(i))
                    eventList.add(event)

                    //Create Calendar object (represents a date) and set to event date
                    val c = Calendar.getInstance()
                    c.clear()
                    c.set(
                        event.bookedDate.year,
                        event.bookedDate.monthValue - 1,
                        event.bookedDate.dayOfMonth
                    )

                    //If entry in map for date doesn't exist, add new entry
                    if (!dayToEvent.containsKey(c)) {
                        dayToEvent[c] = ArrayList<Event>()
                    }
                    //Add event to map at date
                    dayToEvent[c]?.add(event)

                    //Create EventDay at date, with icon as circle, and add to calendarEvents list
                    val e = EventDay(c, R.drawable.background_circle)
                    calendarEvents.add(e)
                }
                //Set all events into calendar
                binding.calendar.setEvents(calendarEvents)
            })

        //When user selects calendar date, displays events on date if they exist
        binding.calendar.setOnDayClickListener {
            if (dayToEvent.containsKey(it.calendar)) {
                createCalendarEventItems(dayToEvent[it.calendar]!!)
            }
        }
    }

    //Creates selectable items for all events on given date
    private fun createCalendarEventItems(events: List<Event>) {
        binding.scrollContainerCalendar.removeAllViews()
        for (e in events) {
            val b = CalendarEventItemBinding.inflate(
                layoutInflater,
                binding.scrollContainerCalendar,
                true
            )
            b.txtCalendarEventDate.text = e.date.toString()
            b.txtCalendarEventName.text = e.eventName

            //On event item is selected, display popup page for event
            b.layoutCalendarEvent.setOnClickListener {
                findNavController().navigate(
                    R.id.action_calendarFragment_to_eventPopupFragment,
                    bundleOf("Event" to e)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CalendarFragment().apply {
            }
    }
}