package com.fliest.passwordview

import android.graphics.Canvas
import android.graphics.Rect

class TextView {
    private val rect = Rect()

    fun drawText(canvas: Canvas, attributes: BaseInputView) {

        if (attributes.boxMargin == 0f) {
            drawTextWithoutMargin(canvas, attributes)
        } else {
            drawTextWithMargin(canvas, attributes)
        }

    }

    private fun drawTextWithMargin(canvas: Canvas, attributes: BaseInputView) {
        val inputCount: Int = attributes.charList.size
        if (inputCount == 0) return

        var left = 0f
        var startX: Float = left + attributes.boxWidth / 2.0f
        val cy = attributes.height / 2.0f


        val dx: Float = attributes.textPaint.measureText("8") / 2.0f
        attributes.textPaint.getTextBounds("8",0,1,rect)
        val dy: Float = (rect.top + rect.bottom)/ 2.0f

        for (i in 0 until inputCount) {

            canvas.drawText(attributes.charList[i].toString(), startX - dx, cy - dy, attributes.textPaint)
            left += attributes.boxMargin + attributes.boxWidth
            startX = left + attributes.boxWidth / 2.0f
        }
    }

    private fun drawTextWithoutMargin(canvas: Canvas, attributes: BaseInputView) {
        val inputCount: Int = attributes.charList.size
        if (inputCount == 0) return

        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        var left = attributes.boxStrokeWidth
        var startX: Float = left + avgWidth / 2.0f
        val cy = attributes.height / 2.0f

        val dx: Float = attributes.textPaint.measureText("8") / 2.0f
        attributes.textPaint.getTextBounds("8",0,1,rect)
        val dy: Float = (rect.top + rect.bottom)/ 2.0f
        for (i in 0 until inputCount) {

            canvas.drawText(attributes.charList[i].toString(), startX - dx, cy - dy, attributes.textPaint)
            left += avgWidth + attributes.boxStrokeWidth
            startX = left + avgWidth / 2.0f
        }
    }

}