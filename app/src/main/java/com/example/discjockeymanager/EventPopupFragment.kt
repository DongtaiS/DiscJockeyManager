package com.example.discjockeymanager

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.databinding.FragmentEventPopupBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventPopupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventPopupFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentEventPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)

        binding = FragmentEventPopupBinding.inflate(layoutInflater)
        val e = arguments?.get("Event") as Event
        Log.i("CREATE", e.toString())
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventPopupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            EventPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}