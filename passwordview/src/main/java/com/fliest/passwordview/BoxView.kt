package com.fliest.passwordview

import android.graphics.Canvas
import android.graphics.Paint

class BoxView {

    fun draw(canvas: Canvas, attributes: BaseInputView) {
        when (attributes.boxStyleType) {
            BaseInputView.BOX_STYLE_TYPE_FILL,
            BaseInputView.BOX_STYLE_TYPE_STROKE -> drawFillOrStrokeBax(canvas, attributes, attributes.boxPaint)

            BaseInputView.BOX_STYLE_TYPE_UNDERLINE -> drawUnderlineBox(canvas, attributes)
        }
    }

    private fun drawFillOrStrokeBax(canvas: Canvas, attributes: BaseInputView, paint: Paint) {
        if (attributes.boxMargin == 0f) {
            drawBoxWithoutMargin(canvas, attributes)
        } else {
            drawBoxWithMargin(canvas, attributes, paint)
        }
    }

    private fun drawBoxWithMargin(canvas: Canvas, attributes: BaseInputView, paint: Paint){
        var left = attributes.boxStrokeWidth / 2.0f
        val top = attributes.boxStrokeWidth / 2.0f
        var right = attributes.boxWidth - left
        val bottom = attributes.boxHeight - top

        for (i in 1..attributes.boxCount) {
            canvas.drawRoundRect(left, top, right, bottom, attributes.boxCorner, attributes.boxCorner, attributes.boxPaint)
            left = right + attributes.boxMargin + attributes.boxStrokeWidth
            right = left + attributes.boxWidth - attributes.boxStrokeWidth
        }
    }

    private fun drawBoxWithoutMargin(canvas: Canvas, attributes: BaseInputView) {
        val left = attributes.boxStrokeWidth / 2.0f
        val top = attributes.boxStrokeWidth / 2.0f
        val right = attributes.width - attributes.boxStrokeWidth / 2.0f
        val bottom = attributes.height - attributes.boxStrokeWidth / 2.0f
        canvas.drawRoundRect(left, top, right, bottom, attributes.boxCorner, attributes.boxCorner, attributes.boxPaint)

        for (i in 1 until attributes.boxCount) {
            canvas.drawLine(left + attributes.boxWidth * i, attributes.boxStrokeWidth, left + attributes.boxWidth * i, attributes.height - attributes.boxStrokeWidth, attributes.boxPaint)
        }
    }

    private fun drawUnderlineBox(canvas: Canvas, attributes: BaseInputView) {
        var startX = 0f
        val startY = attributes.boxHeight - attributes.boxUnderlineHeight / 2.0f
        var stopX = attributes.boxWidth

        for (i in 1..attributes.boxCount) {
            canvas.drawLine(startX, startY, stopX, startY, attributes.boxPaint)
            startX = stopX + attributes.boxMargin
            stopX = startX + attributes.boxWidth
        }
    }
}