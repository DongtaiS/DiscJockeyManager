package com.example.discjockeymanager

import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Used in homepage dashboard charts to format floats into ints
 */

class IntFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "" + value.toInt()
    }
}