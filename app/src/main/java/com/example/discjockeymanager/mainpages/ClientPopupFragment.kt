package com.example.discjockeymanager.mainpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.Client
import com.example.discjockeymanager.databinding.FragmentClientsPopupBinding

/**
 * Popup page displaying all information for one client
 */

class ClientPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentClientsPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClientsPopupBinding.inflate(layoutInflater)

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
        @JvmStatic
        fun newInstance() =
            ClientPopupFragment().apply {
            }
    }
}