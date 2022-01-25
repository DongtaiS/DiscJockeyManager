package com.example.discjockeymanager.mainpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.Staff
import com.example.discjockeymanager.databinding.FragmentStaffPopupBinding

/**
 * Popup page displaying all information for one staff member
 */

class StaffPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentStaffPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStaffPopupBinding.inflate(layoutInflater)

        val s = arguments?.get("Staff") as Staff
        binding.textStaffPopupAddress.text = s.address
        binding.textStaffPopupCell.text = s.cellNumber
        binding.textStaffPopupCity.text = s.city
        binding.textStaffPopupCountry.text = s.country
        binding.textStaffPopupEmail.text = s.email
        binding.textStaffPopupFirstName.text = s.firstName
        binding.textStaffPopupLastName.text = s.lastName
        binding.textStaffPopupStateProvince.text = s.stateProvince
        binding.textStaffZip.text = s.zipcode
        binding.textStaffPopupGender.text = s.gender
        binding.textStaffPopupStaffType.text = s.staffType
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
            StaffPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}