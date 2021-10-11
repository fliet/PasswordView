package com.fliest.passwordview.textview

import android.graphics.Canvas
import com.fliest.passwordview.PasswordView
import com.fliest.passwordview.base.BaseInputView
import com.fliest.passwordview.base.ITextView

class PlaintTextView: ITextView {
    override fun drawText(canvas: Canvas, attributes: BaseInputView) {
        if (attributes !is PasswordView)
            return

        if (attributes.boxMargin == 0f) {
            drawTextWithoutMargin(canvas, attributes)
        } else {
            drawTextWithMargin(canvas, attributes)
        }
    }

    private fun drawTextWithoutMargin(canvas: Canvas, attributes: PasswordView) {

        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        val height = attributes.height
        val cy = height / 2f

        val index = attributes.charList.size - 1
        val text = attributes.charList[index].toString()
        val dx: Float = attributes.textPaint.measureText("8") / 2.0f
        attributes.textPaint.getTextBounds("8", 0, 1, attributes.rect)

        val dy: Float = (attributes.rect.top + attributes.rect.bottom) / 2.0f
        val left = attributes.boxStrokeWidth * (index + 1) + avgWidth * index
        val startX: Float = left + avgWidth / 2

        canvas.drawText(text, startX - dx, cy - dy, attributes.textPaint)
    }

    private fun drawTextWithMargin(canvas: Canvas, attributes: PasswordView) {
        var left = 0f
        val height = attributes.height
        val cy = height / 2f

        val index = attributes.charList.size - 1
        val text = attributes.charList[index].toString()
        val dx: Float = attributes.textPaint.measureText("8") / 2.0f
        attributes.textPaint.getTextBounds("8", 0, 1, attributes.rect)

        val dy: Float = (attributes.rect.top + attributes.rect.bottom) / 2.0f
        if (index != 0) left += (attributes.boxMargin + attributes.boxWidth) * index
        val startX: Float = left + attributes.boxWidth / 2

        canvas.drawText(text, startX - dx, cy - dy, attributes.textPaint)
    }
}