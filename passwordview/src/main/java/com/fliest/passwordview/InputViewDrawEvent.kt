package com.fliest.passwordview

import android.graphics.Canvas
import android.graphics.Paint

interface InputViewDrawEvent {

    fun onDrawInputBox(canvas: Canvas, boxPaint: Paint)
    fun onDrawText(canvas: Canvas, textPaint: Paint)
    fun onDrawCursor(canvas: Canvas, cursorPaint: Paint)
    fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int)
}