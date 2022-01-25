package com.example.discjockeymanager.mainpages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.Venue
import com.example.discjockeymanager.databinding.FragmentVenuesPopupBinding

/**
 * Popup page displaying all information for one venue
 */

class VenuesPopupFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentVenuesPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVenuesPopupBinding.inflate(layoutInflater)

        val v = arguments?.get("Venue") as Venue
        binding.textVenuePopupAddress.text = v.address
        binding.textVenuePopupCity.text = v.city
        binding.textVenuePopupCountry.text = v.country
        binding.textVenuePopupName.text = v.name
        binding.textVenuePopupNotes.text = v.notes
        binding.textVenuePopupStateProvince.text = v.stateProvince
        binding.textVenuePopupZipPostal.text = v.zipcode
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
        fun newInstance(param1: String, param2: String) =
            VenuesPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}