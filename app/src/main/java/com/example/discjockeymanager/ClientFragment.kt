package com.example.discjockeymanager

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.databinding.FragmentClientsBinding
import com.example.discjockeymanager.databinding.TablerowClientsBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentClientsBinding
/**
 * A simple [Fragment] subclass.
 * Use the [ClientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val clientList = ArrayList<Client>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentClientsBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_CLIENTS, JSONObject(),
            {
                Log.i("TESTREQUEST", it.toString())
                val arr = it.getJSONArray("clients")
                for (i in 0 until arr.length()) {
                    clientList.add(Client.parseClient(arr.getJSONObject(i)))
                }
                var even = true
                for (c in clientList) {
                    createTableRow(c, even)
                    even = !even
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createTableRow(c: Client, even: Boolean) {
        val rowBinding = TablerowClientsBinding.inflate(layoutInflater, binding.tableClientsMain, true)
        if (even) {
            val typedValue = TypedValue()
            val theme = requireContext().theme
            theme.resolveAttribute(R.attr.colorSecondaryVariant, typedValue, true)
            @ColorInt val color = typedValue.data
            rowBinding.root.background = ColorDrawable(color)
        }
        rowBinding.textClientsRowName.text = "${c.firstName} ${c.lastName}"
        rowBinding.textClientsRowCellNumber.text = c.cellNumber
        rowBinding.textClientsRowEmail.text = c.email

        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_clientFragment_to_clientPopupFragment,
                bundleOf("Client" to c)
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}