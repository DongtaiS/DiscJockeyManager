package com.example.discjockeymanager

import android.graphics.Canvas
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class ChartToolTip : IMarker {
    override fun getOffset(): MPPointF {
        return MPPointF(0f,0f)
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        return offset
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        TODO("Not yet implemented")
    }
}