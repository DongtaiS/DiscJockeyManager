package com.example.discjockeymanager.mainpages

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.Colors
import com.example.discjockeymanager.databinding.FragmentResourcesBinding
import com.example.discjockeymanager.databinding.TablerowResourcesBinding
import org.json.JSONObject

import com.example.discjockeymanager.objects.Resource
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType

/**
 * Fragment page displaying hardware resources
 */

class ResourcesFragment : Fragment() {
    private lateinit var binding: FragmentResourcesBinding

    //List of resources from database
    private val resourceList = ArrayList<Resource>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentResourcesBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_RESOURCES,
            JSONObject(),
            {
                val arr = it.getJSONArray("resources")
                for (i in 0 until arr.length()) {
                    resourceList.add(Resource.parseResource(arr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (r in resourceList) {
                    createTableRow(r, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(r: Resource, even: Boolean) {
        val rowBinding =
            TablerowResourcesBinding.inflate(layoutInflater, binding.tableResourcesMain, true)
        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }
        rowBinding.textResourcesRowDesc.text = r.desc
        rowBinding.textResourcesRowClass.text = r.resourceClass
        rowBinding.textResourcesRowBrand.text = r.brand
        rowBinding.textResourcesRowNewOld.text = r.newUsed

        //When row is selected, navigates to popup page for selected resource
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_resourcesFragment_to_resourcesPopupFragment,
                bundleOf("Resource" to r)
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
            ResourcesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}