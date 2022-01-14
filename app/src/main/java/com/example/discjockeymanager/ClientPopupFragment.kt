package com.example.discjockeymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.Objects.Client
import com.example.discjockeymanager.databinding.FragmentClientsPopupBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var binding: FragmentClientsPopupBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ClientPopupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientPopupFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)

        binding =  FragmentClientsPopupBinding.inflate(layoutInflater)
        val c = arguments?.get("Client") as Client
        binding.textClientPopupAddress.text = c.address
        binding.textClientPopupCellNumber.text = c.cellNumber
        binding.textClientPopupCity.text = c.city
        binding.textClientPopupCompanyName.text = c.companyName
        binding.textClientPopupCompanyUrl.text = c.company_url
        binding.textClientPopupCountry.text = c.country
        binding.textClientPopupEmail.text = c.email
        binding.textClientPopupFacebookUrl.text = c.facebookUrl
        binding.textClientPopupFirstName.text = c.firstName
        binding.textClientPopupInstagramUrl.text = c.instagramUrl
        binding.textClientPopupLastName.text = c.lastName
        binding.textClientPopupMicrositeUrl.text = c.weddingMicrositeUrl
        binding.textClientPopupPasword.text = c.password
        binding.textClientPopupState.text = c.state
        binding.textClientPopupTwitterUrl.text = c.twitterUrl
        binding.textClientPopupZipcode.text = c.zipcode
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientPopupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}