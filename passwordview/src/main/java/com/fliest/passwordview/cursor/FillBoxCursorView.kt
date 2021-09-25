package com.fliest.passwordview.cursor

import android.graphics.Canvas
import com.fliest.passwordview.base.BaseCursorView
import com.fliest.passwordview.base.BaseInputView

/**
 * Fill Box 光标
 *
 * @author: fliest
 * @date: 2021-09-14 08:40:38
 */
class FillBoxCursorView : BaseCursorView {

    /**
     * 绘制光标
     */
    override fun drawCursor(canvas: Canvas, attributes: BaseInputView) {
        if (attributes.charList.size == attributes.boxCount
            || attributes.cursorHeight == 0f
            || attributes.cursorWidth == 0f
            || attributes.cursorType == BaseInputView.CURSOR_TYPE_LINE
            || attributes.boxStyleType != BaseInputView.BOX_STYLE_TYPE_FILL
        )
            return

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

        val left = index * (attributes.boxWidth + attributes.boxMargin)
        val top = 0f
        val right = index * (attributes.boxWidth + attributes.boxMargin) + attributes.boxWidth
        val bottom = attributes.boxHeight

        canvas.drawRoundRect(left, top, right, bottom, attributes.boxCorner, attributes.boxCorner, attributes.cursorPaint)
    }
}