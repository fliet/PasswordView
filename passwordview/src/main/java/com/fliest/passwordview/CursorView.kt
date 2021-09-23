package com.fliest.passwordview

import android.graphics.Canvas

class CursorView {

    fun drawCursor(canvas: Canvas, attributes: BaseInputView) {
        if (attributes.charList.size == attributes.boxCount || attributes.cursorHeight == 0f || attributes.cursorWidth == 0f)
            return

        if (attributes.boxMargin == 0f) {
            drawCursorWithoutMargin(canvas, attributes)
        } else {
            drawCursorWithMargin(canvas, attributes)
        }


    }

    private fun drawCursorWithMargin(canvas: Canvas, attributes: BaseInputView) {
        val index = if (attributes.charList.size == 0)
            0
        else
            attributes.charList.size


        val startX =
            attributes.boxWidth / 2.0f + index * (attributes.boxWidth + attributes.boxMargin)
        val startY = (attributes.boxHeight - attributes.cursorHeight) / 2.0f
        val stopY = startY + attributes.cursorHeight

        canvas.drawLine(startX, startY, startX, stopY, attributes.cursorPaint)
    }

    private fun drawCursorWithoutMargin(canvas: Canvas, attributes: BaseInputView) {
        val index = if (attributes.charList.size == 0)
            0
        else
            attributes.charList.size


        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        val startX =
            attributes.boxStrokeWidth + avgWidth / 2.0f + index * (avgWidth + attributes.boxStrokeWidth)
        val startY = (attributes.boxHeight - attributes.cursorHeight) / 2.0f
        val stopY = startY + attributes.cursorHeight

        canvas.drawLine(startX, startY, startX, stopY, attributes.cursorPaint)
    }
}