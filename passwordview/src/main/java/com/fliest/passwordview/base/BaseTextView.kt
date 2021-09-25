package com.fliest.passwordview.base

import android.graphics.Canvas

interface BaseTextView {
    fun drawText(canvas: Canvas, attributes: BaseInputView)
}