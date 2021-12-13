package com.example.discjockeymanager

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONObject
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Resource(
    val brand: String,
    val resourceClass: String,
    val purchaseDate: LocalDate,
    val desc: String,
    val id: Int,
    val model: String,
    val originalPurchaseDate: LocalDate,
    val newUsed: String,
    val userId: Int
): Serializable {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun parseResource(r: JSONObject): Resource {
            return Resource(
                r.getString("brand"),
                r.getString("class_of_resource"),
                LocalDate.parse(r.getString("date_purchased")),
                r.getString("descriptive_term"),
                r.getInt("id"),
                r.getString("model_name_num"),
                LocalDate.parse(r.getString("purchase_date_old")),
                r.getString("purchased_new_old"),
                r.getInt("user_id")
            )
        }
    }
}