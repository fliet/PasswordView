package com.fliest.passwordview

import android.graphics.Canvas
import android.graphics.Paint
import com.fliest.passwordview.base.BaseBoxView
import com.fliest.passwordview.base.BaseInputView

/**
 * 边框绘制
 *
 * @author: fliest
 * @date: 2021-09-14 08:38:03
 */
class DefaultBoxView : BaseBoxView{

    /**
     * 绘制边框
     */
    override fun drawBox(canvas: Canvas, attributes: BaseInputView) {
        when (attributes.boxStyleType) {
            BaseInputView.BOX_STYLE_TYPE_FILL,
            BaseInputView.BOX_STYLE_TYPE_STROKE -> drawFillOrStrokeBax(canvas, attributes, attributes.boxPaint)

            BaseInputView.BOX_STYLE_TYPE_UNDERLINE -> drawUnderlineBox(canvas, attributes)
        }
    }

    /**
     * 绘制 fill 或 stroke 边框
     * 二者的区别已经由 Paint 通过 Paint.style 处理了
     */
    private fun drawFillOrStrokeBax(canvas: Canvas, attributes: BaseInputView, paint: Paint) {
        if (attributes.boxMargin == 0f) {
            drawBoxWithoutMargin(canvas, attributes)
        } else {
            drawBoxWithMargin(canvas, attributes, paint)
        }
    }

    /**
     * 绘制有 margin 的边框
     */
    private fun drawBoxWithMargin(canvas: Canvas, attributes: BaseInputView, paint: Paint) {
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

    /**
     * 绘制无 margin 的边框
     */
    private fun drawBoxWithoutMargin(canvas: Canvas, attributes: BaseInputView) {

        val left = attributes.boxStrokeWidth / 2.0f
        val top = attributes.boxStrokeWidth / 2.0f
        val right = attributes.width - attributes.boxStrokeWidth / 2.0f
        val bottom = attributes.height - attributes.boxStrokeWidth / 2.0f
        canvas.drawRoundRect(left, top, right, bottom, attributes.boxCorner, attributes.boxCorner, attributes.boxPaint)

        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        for (i in 1 until attributes.boxCount) {
            canvas.drawLine((attributes.boxStrokeWidth + avgWidth) * i + attributes.boxStrokeWidth / 2.0f,
                attributes.boxStrokeWidth,
                (attributes.boxStrokeWidth + avgWidth) * i + attributes.boxStrokeWidth / 2.0f,
                attributes.height - attributes.boxStrokeWidth,
                attributes.boxPaint)
        }
    }

    /**
     * 绘制下划线边框
     */
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