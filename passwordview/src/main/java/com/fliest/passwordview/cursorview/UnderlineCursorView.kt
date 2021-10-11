package com.fliest.passwordview.cursorview

import android.graphics.Canvas
import com.fliest.passwordview.base.ICursorView
import com.fliest.passwordview.base.BaseInputView

/**
 * 下划线光标
 *
 * @author: fliest
 * @date: 2021-09-14 08:40:38
 */
class UnderlineCursorView : ICursorView {

    /**
     * 绘制光标
     */
    override fun drawCursor(canvas: Canvas, attributes: BaseInputView) {
        if (attributes.charList.size == attributes.boxCount
            || attributes.cursorHeight == 0f
            || attributes.cursorWidth == 0f
            || attributes.cursorType == BaseInputView.CURSOR_TYPE_LINE
            || attributes.boxStyleType != BaseInputView.BOX_STYLE_TYPE_UNDERLINE
        )
            return

        // 无边距情况下，下划线会连成一条直线，此处只保留有边距情况
        if (attributes.boxMargin != 0f)
            drawCursorWithMargin(canvas, attributes)
    }

    /**
     * 绘制边框间有 margin 情形下的光标
     */
    private fun drawCursorWithMargin(canvas: Canvas, attributes: BaseInputView) {
        val index = if (attributes.charList.size == 0)
            0
        else
            attributes.charList.size

        val startX = index * (attributes.boxWidth + attributes.boxMargin)
        val stopX = index * (attributes.boxWidth + attributes.boxMargin) + attributes.boxWidth

        val startY = attributes.boxHeight - attributes.boxUnderlineHeight / 2.0f
        canvas.drawLine(startX, startY, stopX, startY, attributes.cursorPaint)
    }
}