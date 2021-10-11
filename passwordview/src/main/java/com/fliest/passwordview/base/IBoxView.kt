package com.fliest.passwordview.base

import android.graphics.Canvas

interface IBoxView {
    fun drawBox(canvas: Canvas, attributes: BaseInputView)
}