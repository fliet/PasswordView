package com.fliest.passwordview.base

import android.graphics.Canvas

interface BaseCursorView {
    fun drawCursor(canvas: Canvas, attributes: BaseInputView)
}