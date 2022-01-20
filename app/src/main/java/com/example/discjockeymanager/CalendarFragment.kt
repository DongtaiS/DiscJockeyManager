package com.example.discjockeymanager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.utils.DateUtils
import com.example.discjockeymanager.Objects.Event
import com.example.discjockeymanager.databinding.CalendarEventItemBinding
import com.example.discjockeymanager.databinding.FragmentCalendarBinding
import org.json.JSONObject
import java.util.*
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val eventList = ArrayList<Event>()
    private val calendarEvents = ArrayList<EventDay>()
    private lateinit var binding: FragmentCalendarBinding
    private val dayToEvent = HashMap<Calendar, ArrayList<Event>>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentCalendarBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_EVENTS, JSONObject(), {
            Log.i("TESTREQUEST", it.toString())
            val eventArr = it.getJSONArray("events")
            for (i in 0 until eventArr.length()) {
                val event = Event.parseEvent(eventArr.getJSONObject(i))
                eventList.add(event)
                val c = Calendar.getInstance()
                c.clear()
                c.set(event.bookedDate.year, event.bookedDate.monthValue-1, event.bookedDate.dayOfMonth)
                if (!dayToEvent.containsKey(c)) {
                    dayToEvent[c] = ArrayList<Event>()
                }
                dayToEvent[c]?.add(event)
                Log.i("TEST", c.toString())
                val e = EventDay(c, R.drawable.background_circle)
                calendarEvents.add(e)
            }
            binding.calendar.setEvents(calendarEvents)
        })

        binding.calendar.setOnDayClickListener {
            Log.i("TEST", it.calendar.toString())
            if (dayToEvent.containsKey(it.calendar)) {
                Log.i("TEST", "GOOD")
                createCalendarEventItems(dayToEvent[it.calendar]!!)
            }
        }
    }

    fun createCalendarEventItems(events: List<Event>) {
        binding.scrollContainerCalendar.removeAllViews()
        for(e in events) {
            val b = CalendarEventItemBinding.inflate(layoutInflater, binding.scrollContainerCalendar, true)
            b.txtCalendarEventDate.text = e.date.toString()
            b.txtCalendarEventName.text = e.eventName
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}