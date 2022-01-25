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
import com.example.discjockeymanager.objects.DJSystem
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.FragmentSystemsBinding
import com.example.discjockeymanager.databinding.TablerowSystemsBinding
import org.json.JSONObject

/**
 * Fragment page displaying equipment systems
 */

class SystemsFragment : Fragment() {
    private lateinit var binding: FragmentSystemsBinding

    //List of systems from database
    private val systemList = ArrayList<DJSystem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSystemsBinding.inflate(layoutInflater)
        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_SYSTEMS,
            JSONObject(),
            {
                val arr = it.getJSONArray("systems")
                for (i in 0 until arr.length()) {
                    systemList.add(DJSystem.parseSystem(arr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (s in systemList) {
                    createTableRow(s, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(s: DJSystem, even: Boolean) {
        val rowBinding =
            TablerowSystemsBinding.inflate(layoutInflater, binding.tableResourcesMain, true)

        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }
        rowBinding.textSystemsRowName.text = s.systemName
        rowBinding.textSystemsRowPrice.text = "$${s.price}"

        //When row is selected, navigates to popup page for selected system
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_systemsFragment_to_systemsPopupFragment,
                bundleOf("System" to s)
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
            SystemsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}