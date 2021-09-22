package com.fliest.passwordview

import android.graphics.Canvas

class TextView {

    fun drawText(canvas: Canvas, attributes: BaseInputView) {
        val inputCount: Int = attributes.charList.size
        if (inputCount == 0) return

        var left = 0f
        var startX: Float = left + attributes.boxWidth / 2.0f
        val cy = attributes.height / 2.0f

        for (i in 0 until inputCount) {
            val dx: Float = attributes.textPaint.measureText(attributes.charList[i].toString()) / 2.0f
            val dy = (attributes.textPaint.fontMetrics.ascent + attributes.textPaint.fontMetrics.descent) / 2.0f

            canvas.drawText(attributes.charList[i].toString(), startX - dx, cy - dy, attributes.textPaint)
            left += attributes.boxMargin + attributes.boxWidth
            startX = left + attributes.boxWidth / 2.0f
        }
    }

}