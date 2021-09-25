package com.fliest.passwordview.base

import android.graphics.Canvas

interface BaseBoxView {
    fun drawBox(canvas: Canvas, attributes: BaseInputView)
}