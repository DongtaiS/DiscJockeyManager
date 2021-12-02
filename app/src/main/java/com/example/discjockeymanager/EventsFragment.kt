package com.example.discjockeymanager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.discjockeymanager.databinding.FragmentEventPopupBinding
import com.example.discjockeymanager.databinding.FragmentEventsBinding
import com.example.discjockeymanager.databinding.PopupEventsBinding
import com.example.discjockeymanager.databinding.TablerowEventsBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

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

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_EVENTS, JSONObject(),
            SharedPreferenceHelper.getAccessToken(requireContext())!!, {
                Log.i("TESTREQUEST", it.toString())
                val eventArr = it.getJSONArray("events")
                for (i in 0 until eventArr.length()) {
                    eventList.add(Event.parseEvent(eventArr.getJSONObject(i)))
                }
                for (e in eventList) {
                    createTableRow(e)
                }
            })
    }

    private fun createTableRow(e: Event) {
        val rowBinding = TablerowEventsBinding.inflate(layoutInflater, binding.tableEventsMain, true)
        rowBinding.textEventsRowName.text = e.eventName
        rowBinding.textEventsRowClient.text = e.client
        rowBinding.textEventsRowDate.text = e.date.toString()
        rowBinding.textEventsRowVenue.text = e.venue

        rowBinding.root.setOnClickListener {
/*            val popupBinding = FragmentEventPopupBinding.inflate(layoutInflater, binding.root, true)
            popupBinding.textEventPopupName.text = e.eventName
            popupBinding.textEventPopupBookedDate.text = e.bookedDate.toString()
            popupBinding.textEventPopupClient.text = e.client
            popupBinding.textEventPopupDate.text = e.date.toString()
            popupBinding.textEventPopupFinishTime.text = e.finishTime.toString()
            popupBinding.textEventPopupLoadingTime.text = e.loadingTime.toString()
            popupBinding.textEventPopupService.text = e.service
            popupBinding.textEventPopupSongs.text = e.songs
            popupBinding.textEventPopupStaff.text = e.staff.toString()
            popupBinding.textEventPopupStartTime.text = e.startTime.toString()
            popupBinding.textEventPopupSystem.text = e.system
            popupBinding.textEventPopupVenue.text = e.venue*/
/*            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popupBinding.root.remove
                }

            }*/
            findNavController().navigate(
                R.id.action_eventsFragment_to_eventPopupFragment2,
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
        //return inflater.inflate(R.layout.fragment_events, container, false)
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