package com.example.discjockeymanager.mainpages


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.Colors
import com.example.discjockeymanager.objects.Client
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.FragmentClientsBinding
import com.example.discjockeymanager.databinding.TablerowClientsBinding
import org.json.JSONObject

/**
 * Fragment page displaying clients
 */

class ClientFragment : Fragment() {
    private lateinit var binding: FragmentClientsBinding

    //List of clients from database
    private val clientList = ArrayList<Client>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClientsBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_CLIENTS,
            JSONObject(),
            {
                val arr = it.getJSONArray("clients")
                for (i in 0 until arr.length()) {
                    clientList.add(Client.parseClient(arr.getJSONObject(i)))
                }

                //Creates rows from client data, alternating in color
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
        val rowBinding =
            TablerowClientsBinding.inflate(layoutInflater, binding.tableClientsMain, true)
        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }
        rowBinding.textClientsRowName.text = "${c.firstName} ${c.lastName}"
        rowBinding.textClientsRowCellNumber.text = c.cellNumber
        rowBinding.textClientsRowEmail.text = c.email

        //When row is selected, navigates to popup page for selected client
        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_clientFragment_to_clientPopupFragment,
                bundleOf("Client" to c)
            )
        }
    }

    companion object {
        fun newInstance() =
            ClientFragment().apply {
            }
    }
}