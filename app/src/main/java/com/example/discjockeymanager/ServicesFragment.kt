package com.example.discjockeymanager

import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.databinding.FragmentServicesBinding
import com.example.discjockeymanager.databinding.TablerowClientsBinding
import com.example.discjockeymanager.databinding.TablerowServicesBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ServicesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServicesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentServicesBinding

    private val serviceList = ArrayList<DJService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentServicesBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_SERVICES, JSONObject(),
            SharedPreferenceHelper.getAccessToken(requireContext())!!, {
                Log.i("TESTREQUEST", it.toString())
                val arr = it.getJSONArray("services")
                for (i in 0 until arr.length()) {
                    serviceList.add(DJService.parseService(arr.getJSONObject(i)))
                }
                var even = true
                for (s in serviceList) {
                    createTableRow(s, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(s: DJService, even: Boolean) {
        val rowBinding = TablerowServicesBinding.inflate(layoutInflater, binding.tableServicesMain, true)
        if (even) {
            val typedValue = TypedValue()
            val theme = requireContext().theme
            theme.resolveAttribute(R.attr.colorSecondaryVariant, typedValue, true)
            @ColorInt val color = typedValue.data
            rowBinding.root.background = ColorDrawable(color)
        }
        rowBinding.textServicesRowName.text = s.name
        rowBinding.textServicesRowDesc.text = s.desc
        rowBinding.textServicesRowPrice.text = "$${s.price}"
        val cleanImage: String = s.image.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "")
        val decoded = Base64.decode(cleanImage, Base64.DEFAULT)
        rowBinding.imgServicesRow.setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.size))

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ServicesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ServicesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}