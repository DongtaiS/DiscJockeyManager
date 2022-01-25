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
import com.example.discjockeymanager.objects.Staff
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.FragmentStaffBinding
import com.example.discjockeymanager.databinding.TablerowClientsBinding
import org.json.JSONObject

/**
 * Fragment page displaying staff members/employees
 */

class StaffFragment : Fragment() {
    private lateinit var binding: FragmentStaffBinding

    //List of staff from database
    private val staffList = ArrayList<Staff>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStaffBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_STAFF,
            JSONObject(),
            {
                val arr = it.getJSONArray("staff")
                for (i in 0 until arr.length()) {
                    staffList.add(Staff.parseStaff(arr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (s in staffList) {
                    createTableRow(s, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(s: Staff, even: Boolean) {
        val rowBinding = TablerowClientsBinding.inflate(layoutInflater, binding.tableStaffMain, true)

        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }

        rowBinding.textClientsRowName.text = "${s.firstName} ${s.lastName}"
        rowBinding.textClientsRowCellNumber.text = s.cellNumber
        rowBinding.textClientsRowEmail.text = s.email

        //When row is selected, navigates to popup page for selected employee
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_staffFragment_to_staffPopupFragment,
                bundleOf("Staff" to s)
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
        fun newInstance(param1: String, param2: String) =
            StaffFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}