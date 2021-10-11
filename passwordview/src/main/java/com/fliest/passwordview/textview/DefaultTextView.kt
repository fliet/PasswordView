package com.fliest.passwordview.textview

import android.graphics.Canvas
import android.graphics.Rect
import com.fliest.passwordview.base.BaseInputView
import com.fliest.passwordview.base.ITextView

/**
 * 文字绘制
 *
 * @author: fliest
 * @date: 2021-09-14 08:38:03
 */
class DefaultTextView : ITextView {
    private val rect = Rect()

    /**
     * 绘制文字
     */
    override fun drawText(canvas: Canvas, attributes: BaseInputView) {

        if (attributes.boxMargin == 0f) {
            drawTextWithoutMargin(canvas, attributes)
        } else {
            drawTextWithMargin(canvas, attributes)
        }

    }

    /**
     * 绘制边框间有 margin 情形下的文字
     */
    private fun drawTextWithMargin(canvas: Canvas, attributes: BaseInputView) {
        val inputCount: Int = attributes.charList.size
        if (inputCount == 0) return

        var left = 0f
        var startX: Float = left + attributes.boxWidth / 2.0f
        val cy = attributes.height / 2.0f

        val dx: Float = attributes.textPaint.measureText("8") / 2.0f
        attributes.textPaint.getTextBounds("8", 0, 1, rect)
        val dy: Float = (rect.top + rect.bottom) / 2.0f

        for (i in 0 until inputCount) {

            canvas.drawText(attributes.charList[i].toString(), startX - dx, cy - dy, attributes.textPaint)
            left += attributes.boxMargin + attributes.boxWidth
            startX = left + attributes.boxWidth / 2.0f
        }
    }

    /**
     * 绘制边框间无 margin 情形下的文字
     */
    private fun drawTextWithoutMargin(canvas: Canvas, attributes: BaseInputView) {
        val inputCount: Int = attributes.charList.size
        if (inputCount == 0) return

        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        var left = attributes.boxStrokeWidth
        var startX: Float = left + avgWidth / 2.0f
        val cy = attributes.height / 2.0f

        val dx: Float = attributes.textPaint.measureText("8") / 2.0f
        attributes.textPaint.getTextBounds("8", 0, 1, rect)
        val dy: Float = (rect.top + rect.bottom) / 2.0f
        for (i in 0 until inputCount) {

            canvas.drawText(attributes.charList[i].toString(), startX - dx, cy - dy, attributes.textPaint)
            left += avgWidth + attributes.boxStrokeWidth
            startX = left + avgWidth / 2.0f
        }
    }

}