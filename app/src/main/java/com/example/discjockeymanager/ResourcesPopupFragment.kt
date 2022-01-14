package com.example.discjockeymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.Objects.Resource
import com.example.discjockeymanager.databinding.FragmentResourcesPopupBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResourcesPopupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResourcesPopupFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentResourcesPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentResourcesPopupBinding.inflate(layoutInflater)
        val r = arguments?.get("Resource") as Resource
        binding.textResourcePopupBrand.text = r.brand
        binding.textResourcePopupClass.text = r.resourceClass
        binding.textResourcePopupDesc.text = r.desc
        binding.textResourcePopupModel.text = r.model
        binding.textResourcePopupNewOld.text = r.newUsed
        if (r.newUsed == "New") {
            binding.textEventPopupLabel3.visibility = View.GONE
            binding.textResourcePopupOriginalPurchaseDate.visibility = View.GONE
        } else {
            binding.textResourcePopupOriginalPurchaseDate.text = r.originalPurchaseDate.toString()
        }
        binding.textResourcePopupPurchaseDate.text = r.purchaseDate.toString()
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
         * @return A new instance of fragment ResourcesPopupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResourcesPopupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}