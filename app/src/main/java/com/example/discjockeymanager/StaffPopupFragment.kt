package com.example.discjockeymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.Objects.Staff
import com.example.discjockeymanager.databinding.FragmentStaffPopupBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StaffPopupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StaffPopupFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentStaffPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentStaffPopupBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StaffPopupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StaffPopupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}