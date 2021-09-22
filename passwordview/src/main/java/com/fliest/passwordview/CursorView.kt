package com.fliest.passwordview

import android.graphics.Canvas

class CursorView{

    fun drawCursor(canvas: Canvas, attributes: BaseInputView) {
        if (attributes.charList.size == attributes.boxCount || attributes.cursorHeight == 0f || attributes.cursorWidth == 0f)
            return

        val index = if (attributes.charList.size == 0)
            0
        else
            attributes.charList.size

        val startX = attributes.boxWidth / 2.0f + index * (attributes.boxWidth + attributes.boxMargin)
        val startY = (attributes.boxHeight - attributes.cursorHeight) / 2.0f
        val stopY = startY + attributes.cursorHeight

        canvas.drawLine(startX, startY, startX, stopY, attributes.cursorPaint)
    }
}