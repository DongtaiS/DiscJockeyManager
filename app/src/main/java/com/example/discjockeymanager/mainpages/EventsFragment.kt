package com.example.discjockeymanager.mainpages

import android.graphics.drawable.ColorDrawable
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
import com.example.discjockeymanager.*
import com.example.discjockeymanager.objects.Event
import com.example.discjockeymanager.databinding.FragmentEventsBinding
import com.example.discjockeymanager.databinding.TablerowEventsBinding
import org.json.JSONObject

/**
 * Fragment page displaying clients
 */

class EventsFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding

    //List of clients from database
    private val eventList = ArrayList<Event>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventsBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_EVENTS,
            JSONObject(),
            {
                val eventArr = it.getJSONArray("events")
                for (i in 0 until eventArr.length()) {
                    eventList.add(Event.parseEvent(eventArr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (e in eventList) {
                    createTableRow(e, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(e: Event, even: Boolean) {
        val rowBinding =
            TablerowEventsBinding.inflate(layoutInflater, binding.tableEventsMain, true)

        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }
        rowBinding.textEventsRowName.text = e.eventName
        rowBinding.textEventsRowClient.text = e.client
        rowBinding.textEventsRowDate.text = e.date.toString()
        rowBinding.textEventsRowVenue.text = e.venue

        //When row is selected, navigates to popup page for selected event
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_eventsFragment_to_eventPopupFragment,
                bundleOf("Event" to e)
            )
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
            EventsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}