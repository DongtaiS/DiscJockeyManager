package com.example.discjockeymanager

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
import com.example.discjockeymanager.databinding.FragmentResourcesBinding
import com.example.discjockeymanager.databinding.TablerowResourcesBinding
import org.json.JSONObject
import androidx.annotation.ColorInt
import android.content.res.Resources.Theme

import android.util.TypedValue







// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResourcesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResourcesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentResourcesBinding
    private val resourceList = ArrayList<Resource>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentResourcesBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(requireContext(), RequestType.GET_RESOURCES, JSONObject(), {
                Log.i("TESTREQUEST", it.toString())
                val arr = it.getJSONArray("resources")
                for (i in 0 until arr.length()) {
                    resourceList.add(Resource.parseResource(arr.getJSONObject(i)))
                }
                var even = true
                for (r in resourceList) {
                    createTableRow(r, even)
                    even = !even
                }
            })
    }
    private fun createTableRow(r: Resource, even: Boolean) {
        val rowBinding = TablerowResourcesBinding.inflate(layoutInflater, binding.tableResourcesMain, true)
        if (even) {
            val typedValue = TypedValue()
            val theme = requireContext().theme
            theme.resolveAttribute(R.attr.colorSecondaryVariant, typedValue, true)
            @ColorInt val color = typedValue.data
            rowBinding.root.background = ColorDrawable(color)
        }
        rowBinding.textResourcesRowDesc.text = r.desc
        rowBinding.textResourcesRowClass.text = r.resourceClass
        rowBinding.textResourcesRowBrand.text = r.brand
        rowBinding.textResourcesRowNewOld.text = r.newUsed

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResourcesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResourcesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}