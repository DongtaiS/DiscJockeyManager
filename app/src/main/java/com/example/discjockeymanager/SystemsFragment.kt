package com.example.discjockeymanager

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.discjockeymanager.Objects.DJSystem
import com.example.discjockeymanager.databinding.FragmentSystemsBinding
import com.example.discjockeymanager.databinding.TablerowSystemsBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SystemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SystemsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSystemsBinding
    private val systemList = ArrayList<DJSystem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentSystemsBinding.inflate(layoutInflater)
        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_SYSTEMS, JSONObject(), {
                Log.i("TESTREQUEST", it.toString())
                val arr = it.getJSONArray("systems")
                for (i in 0 until arr.length()) {
                    systemList.add(DJSystem.parseSystem(arr.getJSONObject(i)))
                }
                var even = true
                for (s in systemList) {
                    createTableRow(s, even)
                    even = !even
                }
            })
    }
    private fun createTableRow(s: DJSystem, even: Boolean) {
        val rowBinding = TablerowSystemsBinding.inflate(layoutInflater, binding.tableResourcesMain, true)
        if (even) {
            rowBinding.root.background = ColorDrawable(Colors.getThemeColor(requireContext(), R.attr.darkBackground))
        }
        rowBinding.textSystemsRowName.text = s.systemName
        rowBinding.textSystemsRowPrice.text = "$${s.price}"

        rowBinding.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_systemsFragment_to_systemsPopupFragment,
                bundleOf("System" to s)
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
         * @return A new instance of fragment SystemsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SystemsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}