package com.example.discjockeymanager.mainpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.Event
import com.example.discjockeymanager.databinding.FragmentEventsPopupBinding

/**
 * Popup page displaying all information for one event
 */

class EventsPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentEventsPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventsPopupBinding.inflate(layoutInflater)

        val e = arguments?.get("Event") as Event
        binding.textEventPopupName.text = e.eventName
        binding.textEventPopupBookedDate.text = e.bookedDate.toString()
        binding.textEventPopupClient.text = e.client
        binding.textEventPopupDate.text = e.date.toString()
        binding.textEventPopupFinishTime.text = e.finishTime.toString()
        binding.textEventPopupLoadingTime.text = e.loadingTime.toString()
        binding.textEventPopupService.text = e.service
        binding.textEventPopupSongs.text = e.songs
        binding.textEventPopupStaff.text = e.staff.toString()
        binding.textEventPopupStartTime.text = e.startTime.toString()
        binding.textEventPopupSystem.text = e.system
        binding.textEventPopupVenue.text = e.venue
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
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
            EventsPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}