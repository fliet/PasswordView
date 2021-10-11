package com.fliest.passwordview.base

import android.graphics.Canvas

interface ICursorView {
    fun drawCursor(canvas: Canvas, attributes: BaseInputView)
}