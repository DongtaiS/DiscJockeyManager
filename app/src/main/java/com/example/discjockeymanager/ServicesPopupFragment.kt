package com.example.discjockeymanager

import android.app.Service
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.databinding.FragmentServicesPopupBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ServicesPopupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServicesPopupFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentServicesPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        binding = FragmentServicesPopupBinding.inflate(layoutInflater)
        val s = arguments?.get("Service") as DJService
        binding.textServicesPopupName.text = s.name
        binding.textServicesPopupDesc.text = s.desc
        binding.textServicesPopupPrice.text = "$${s.price}"
        val cleanImage: String = s.image.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "")
        val decoded = Base64.decode(cleanImage, Base64.DEFAULT)
        binding.imgServicesPopup.setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.size))
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
         * @return A new instance of fragment ServicesPopupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ServicesPopupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}