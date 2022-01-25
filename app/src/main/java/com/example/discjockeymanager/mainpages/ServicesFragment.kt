package com.example.discjockeymanager.mainpages

import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.Colors
import com.example.discjockeymanager.objects.DJService
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.FragmentServicesBinding
import com.example.discjockeymanager.databinding.TablerowServicesBinding
import org.json.JSONObject

/**
 * Fragment page displaying DJ services
 */

class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding

    //List of services from database
    private val serviceList = ArrayList<DJService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentServicesBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_SERVICES,
            JSONObject(),
            {
                val arr = it.getJSONArray("services")
                for (i in 0 until arr.length()) {
                    serviceList.add(DJService.parseService(arr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (s in serviceList) {
                    createTableRow(s, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(s: DJService, even: Boolean) {
        val rowBinding = TablerowServicesBinding.inflate(layoutInflater, binding.tableServicesMain, true)

        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }

        rowBinding.textServicesRowName.text = s.name
        rowBinding.textServicesRowDesc.text = s.desc
        rowBinding.textServicesRowPrice.text = "$${s.price}"

        //Cleans raw image string and decodes from base64 to bytearray, then to bitmap
        val cleanImage: String = s.image.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "")
        val decoded = Base64.decode(cleanImage, Base64.DEFAULT)
        rowBinding.imgServicesRow.setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.size))

        //When row is selected, navigates to popup page for selected service
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_servicesFragment_to_servicesPopupFragment,
                bundleOf("Service" to s)
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
            ServicesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}