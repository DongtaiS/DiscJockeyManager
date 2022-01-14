package com.example.discjockeymanager

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.Objects.Venue
import com.example.discjockeymanager.databinding.FragmentVenuesBinding
import com.example.discjockeymanager.databinding.TablerowVenuesBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VenueFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VenueFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentVenuesBinding
    private val venueList = ArrayList<Venue>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentVenuesBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_VENUES, JSONObject(), {
                Log.i("TESTREQUEST", it.toString())
                val eventArr = it.getJSONArray("venues")
                for (i in 0 until eventArr.length()) {
                    venueList.add(Venue.parseVenue(eventArr.getJSONObject(i)))
                }
                var even = true
                for (v in venueList) {
                    createTableRow(v, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(v: Venue, even: Boolean) {
        val rowBinding = TablerowVenuesBinding.inflate(layoutInflater, binding.tableVenuesMain, true)
        if (even) {
            rowBinding.root.background = ColorDrawable(Colors.getThemeColor(requireContext(), R.attr.darkBackground))
        }
        rowBinding.textVenuesRowNameRowName.text = v.name
        rowBinding.textVenuesRowAddress.text = v.address
        rowBinding.textVenuesRowCity.text = v.city
        rowBinding.textVenuesRowStateProvince.text = v.stateProvince

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VenueFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VenueFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}