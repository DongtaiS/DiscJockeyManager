package com.example.discjockeymanager

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
import com.example.discjockeymanager.Objects.Event
import com.example.discjockeymanager.databinding.FragmentEventsBinding
import com.example.discjockeymanager.databinding.TablerowEventsBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentEventsBinding
    private val eventList = ArrayList<Event>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentEventsBinding.inflate(layoutInflater)

        Log.i("TESTREQUEST", SharedPreferenceHelper.getRefreshToken(requireContext()) ?: "FAIL")

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_EVENTS, JSONObject(), {
                Log.i("TESTREQUEST", it.toString())
                val eventArr = it.getJSONArray("events")
                for (i in 0 until eventArr.length()) {
                    eventList.add(Event.parseEvent(eventArr.getJSONObject(i)))
                }
                var even = true
                for (e in eventList) {
                    createTableRow(e, even)
                    even = !even
                }
            })
    }
    private fun createTableRow(e: Event, even: Boolean) {
        val rowBinding = TablerowEventsBinding.inflate(layoutInflater, binding.tableEventsMain, true)
        if (even) {
            rowBinding.root.background = ColorDrawable(Colors.getThemeColor(requireContext(), R.attr.darkBackground))
        }
        rowBinding.textEventsRowName.text = e.eventName
        rowBinding.textEventsRowClient.text = e.client
        rowBinding.textEventsRowDate.text = e.date.toString()
        rowBinding.textEventsRowVenue.text = e.venue

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}