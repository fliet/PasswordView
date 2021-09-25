package com.fliest.passwordview.cursor

import android.graphics.Canvas
import com.fliest.passwordview.base.BaseCursorView
import com.fliest.passwordview.base.BaseInputView

/**
 * 边框光标
 *
 * @author: fliest
 * @date: 2021-09-14 08:40:38
 */
class BoxCursorView : BaseCursorView {

    /**
     * 绘制光标
     */
    override fun drawCursor(canvas: Canvas, attributes: BaseInputView) {
        if (attributes.charList.size == attributes.boxCount
            || attributes.cursorHeight == 0f
            || attributes.cursorWidth == 0f
            || attributes.cursorType == BaseInputView.CURSOR_TYPE_LINE
            || attributes.boxStyleType != BaseInputView.BOX_STYLE_TYPE_STROKE
        )
            return

        if (attributes.boxMargin == 0f) {
            drawCursorWithoutMargin(canvas, attributes)
        } else {
            drawCursorWithMargin(canvas, attributes)
        }
    }

    /**
     * 绘制边框间有 margin 情形下的光标
     */
    private fun drawCursorWithMargin(canvas: Canvas, attributes: BaseInputView) {
        val index = if (attributes.charList.size == 0)
            0
        else
            attributes.charList.size

        val left =
            attributes.cursorWidth / 2.0f + index * (attributes.boxWidth + attributes.boxMargin)
        val top = attributes.cursorWidth / 2.0f
        val right =
            index * (attributes.boxWidth + attributes.boxMargin) + attributes.boxWidth - attributes.cursorWidth / 2.0f
        val bottom = attributes.height - attributes.cursorWidth / 2.0f

        canvas.drawRoundRect(left, top, right, bottom, attributes.boxCorner, attributes.boxCorner, attributes.cursorPaint)
    }

    /**
     * 绘制边框间无 margin 情形下的光标
     */
    private fun drawCursorWithoutMargin(canvas: Canvas, attributes: BaseInputView) {
        val index =
            if (attributes.charList.size == 0)
                0
            else
                attributes.charList.size

        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        val left =
            attributes.boxStrokeWidth / 2.0f + index * (avgWidth + attributes.boxStrokeWidth)
        val top = attributes.boxStrokeWidth / 2.0f
        val right = left + avgWidth +  attributes.boxStrokeWidth
        val bottom = attributes.height - attributes.boxStrokeWidth / 2.0f

        canvas.drawRoundRect(left, top, right, bottom, attributes.boxCorner, attributes.boxCorner, attributes.cursorPaint)
    }
}