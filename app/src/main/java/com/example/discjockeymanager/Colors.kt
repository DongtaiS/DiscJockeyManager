package com.example.discjockeymanager

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import androidx.annotation.ColorInt

class Colors {
    companion object {
        @ColorInt
        fun getThemeColor(context: Context, attribute: Int) : Int {
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(attribute, typedValue, true)
            return typedValue.data
        }
    }
}