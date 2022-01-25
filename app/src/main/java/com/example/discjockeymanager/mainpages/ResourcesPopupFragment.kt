package com.example.discjockeymanager.mainpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.Resource
import com.example.discjockeymanager.databinding.FragmentResourcesPopupBinding

/**
 * Popup page displaying all information for one equipment resource
 */

class ResourcesPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentResourcesPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentResourcesPopupBinding.inflate(layoutInflater)

        val r = arguments?.get("Resource") as Resource
        binding.textResourcePopupBrand.text = r.brand
        binding.textResourcePopupClass.text = r.resourceClass
        binding.textResourcePopupDesc.text = r.desc
        binding.textResourcePopupModel.text = r.model
        binding.textResourcePopupNewOld.text = r.newUsed

        //Hide original purchase date if new
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
        @JvmStatic
        fun newInstance() =
            ResourcesPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}