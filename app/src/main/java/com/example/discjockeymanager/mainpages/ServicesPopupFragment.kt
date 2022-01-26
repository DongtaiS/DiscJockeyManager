package com.example.discjockeymanager.mainpages

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.DJService
import com.example.discjockeymanager.databinding.FragmentServicesPopupBinding

/**
 * Popup page displaying all information for one DJ service
 */

class ServicesPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentServicesPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentServicesPopupBinding.inflate(layoutInflater)

        val s = arguments?.get("Service") as DJService
        binding.textServicesPopupName.text = s.name
        binding.textServicesPopupDesc.text = s.desc
        binding.textServicesPopupPrice.text = "$${s.price}"

        //Cleans raw image string and decodes from base64 to bytearray, then to bitmap
        val cleanImage: String = s.image.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "")
        val decoded = Base64.decode(cleanImage, Base64.DEFAULT)
        binding.imgServicesPopup.setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.size))
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
            ServicesPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}