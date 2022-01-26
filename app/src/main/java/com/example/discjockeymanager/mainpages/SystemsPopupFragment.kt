package com.example.discjockeymanager.mainpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.discjockeymanager.objects.DJSystem
import com.example.discjockeymanager.databinding.FragmentSystemsPopupBinding

/**
 * Popup page displaying all information for one equipment system
 */

class SystemsPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentSystemsPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSystemsPopupBinding.inflate(layoutInflater)

        val s = arguments?.get("System") as DJSystem
        binding.textSystemPopupCableBox.text = s.cableBox
        binding.textSystemPopupLights.text = s.lights
        binding.textSystemPopupMixer.text = s.mixer
        binding.textSystemPopupName.text = s.systemName
        binding.textSystemPopupPlayers.text = s.players
        binding.textSystemPopupPrice.text = "$${s.price}"
        binding.textSystemPopupSpeakers.text = s.speakers
        binding.textSystemPopupTrussSystem.text = s.trussSystem
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
            SystemsPopupFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}