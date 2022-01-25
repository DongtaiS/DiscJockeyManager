package com.example.discjockeymanager.mainpages

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.Colors
import com.example.discjockeymanager.objects.Venue
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.FragmentVenuesBinding
import com.example.discjockeymanager.databinding.TablerowVenuesBinding
import org.json.JSONObject

/**
 * Fragment page displaying venues
 */

class VenueFragment : Fragment() {
    private lateinit var binding: FragmentVenuesBinding

    //List of venues from database
    private val venueList = ArrayList<Venue>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVenuesBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_VENUES,
            JSONObject(),
            {
                val eventArr = it.getJSONArray("venues")
                for (i in 0 until eventArr.length()) {
                    venueList.add(Venue.parseVenue(eventArr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (v in venueList) {
                    createTableRow(v, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(v: Venue, even: Boolean) {
        val rowBinding =
            TablerowVenuesBinding.inflate(layoutInflater, binding.tableVenuesMain, true)

        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }

        rowBinding.textVenuesRowNameRowName.text = v.name
        rowBinding.textVenuesRowAddress.text = v.address
        rowBinding.textVenuesRowCity.text = v.city
        rowBinding.textVenuesRowStateProvince.text = v.stateProvince

        //When row is selected, navigates to popup page for selected venue
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_venueFragment_to_venuesPopupFragment,
                bundleOf("Venue" to v)
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
            VenueFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}