package com.example.discjockeymanager.mainpages

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.Colors
import com.example.discjockeymanager.objects.Song
import com.example.discjockeymanager.R
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.FragmentSongsBinding
import com.example.discjockeymanager.databinding.TablerowSongsBinding
import org.json.JSONObject

/**
 * Fragment page displaying songs
 */

class SongsFragment : Fragment() {
    private lateinit var binding: FragmentSongsBinding

    //List of songs from database
    private val songList = ArrayList<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSongsBinding.inflate(layoutInflater)

        APIRequestHelper.jsonRequestWithAuth(
            requireContext(),
            RequestType.GET_SONGS,
            JSONObject(),
            {
                val arr = it.getJSONArray("songs")
                for (i in 0 until arr.length()) {
                    songList.add(Song.parseSong(arr.getJSONObject(i)))
                }

                //Creates rows from data, alternating in color
                var even = true
                for (s in songList) {
                    createTableRow(s, even)
                    even = !even
                }
            })
    }

    private fun createTableRow(s: Song, even: Boolean) {
        val rowBinding = TablerowSongsBinding.inflate(layoutInflater, binding.tableSongsMain, true)

        //Changes background color if even numbered row
        if (even) {
            rowBinding.root.background = ColorDrawable(
                Colors.getThemeColor(
                    requireContext(),
                    R.attr.darkBackground
                )
            )
        }
        rowBinding.textSongsRowName.text = s.songName
        rowBinding.textSongsRowArtist.text = s.artist
        rowBinding.textSongsRowBPM.text = s.bpm
        rowBinding.textSongsRowMixName.text = s.mixName
        rowBinding.textSongsRowLabel.text = s.label
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
            SongsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}