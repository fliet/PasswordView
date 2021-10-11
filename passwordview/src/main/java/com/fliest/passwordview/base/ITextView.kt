package com.fliest.passwordview.base

import android.graphics.Canvas

interface ITextView {
    fun drawText(canvas: Canvas, attributes: BaseInputView)
}