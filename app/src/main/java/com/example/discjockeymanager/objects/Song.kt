package com.example.discjockeymanager.objects


import org.json.JSONObject
import java.io.Serializable
/**
 * Data class representing song, including function to parse from JSONObject
 */

data class Song(
    val artist: String,
    val bpm: String,
    val id: Int,
    val label: String,
    val mixName: String,
    val songName: String
) : Serializable {
    companion object {
        fun parseSong(s: JSONObject) : Song {
            return Song(
                s.getString("artist"),
                s.getString("bpm"),
                s.getInt("id"),
                s.getString("label"),
                s.getString("mix_name"),
                s.getString("song_name")
            )
        }
    }
}