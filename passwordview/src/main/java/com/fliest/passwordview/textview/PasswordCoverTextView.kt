package com.fliest.passwordview.textview

import android.graphics.Canvas
import android.graphics.Paint
import com.fliest.passwordview.PasswordView
import com.fliest.passwordview.base.BaseInputView
import com.fliest.passwordview.base.ITextView

class PasswordCoverTextView: ITextView {
    override fun drawText(canvas: Canvas, attributes: BaseInputView) {
        if (attributes !is PasswordView)
            return
        if (attributes.pwdCoverType == BaseInputView.PWD_COVER_TYPE_DOT)
            drawCoverDot(canvas, attributes)
        else if (attributes.pwdCoverType == BaseInputView.PWD_COVER_TYPE_STAR)
            drawCoverStar(canvas, attributes)
    }

    private fun drawCoverDot(canvas: Canvas, attributes: PasswordView) {
        if (attributes.boxMargin == 0f)
            drawCoverDotWithoutMargin(canvas, attributes)
        else
            drawCoverDotWithMargin(canvas, attributes)
    }

    private fun drawCoverDotWithMargin(canvas: Canvas, attributes: PasswordView) {
        var left = attributes.boxStrokeWidth / 2
        val height = attributes.height
        val cy = height / 2f
        for (i in 0 until attributes.coverCount) {
            if (i != 0) left += (attributes.boxMargin + attributes.boxWidth)
            val startX = left + attributes.boxWidth / 2
            canvas.drawCircle(startX, cy, attributes.dotRadius, attributes.dotPaint)
        }
    }

    private fun drawCoverDotWithoutMargin(canvas: Canvas, attributes: PasswordView) {
        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        var startX = attributes.boxStrokeWidth + avgWidth / 2
        val height = attributes.height
        val cy = height / 2f
        for (i in 0 until attributes.coverCount) {
            canvas.drawCircle(startX, cy, attributes.dotRadius, attributes.dotPaint)
            startX += attributes.boxStrokeWidth + avgWidth
        }
    }

    private fun drawCoverStar(canvas: Canvas,  attributes: PasswordView) {
        if (attributes.charList.size == 0) return

        if (attributes.boxMargin == 0f) {
            drawCoverStarWithoutMargin(canvas, attributes)
        } else {
            drawCoverStarWithMargin(canvas, attributes)
        }
    }

    private fun drawCoverStarWithMargin(canvas: Canvas, attributes: PasswordView) {
        var left = 0f
        var startX: Float = left + attributes.boxWidth / 2.0f
        val cy = attributes.height / 2.0f
        val dx: Float = attributes.starPaint.measureText("*") / 2.0f
        attributes.starPaint.getTextBounds("*", 0, 1, attributes.rect)
        val dy: Float = (attributes.rect.top + attributes.rect.bottom) / 2.0f

        for (i in 0 until attributes.coverCount) {
            canvas.drawText("*", startX - dx, cy - dy, attributes.starPaint)
            left += attributes.boxMargin + attributes.boxWidth
            startX = left + attributes.boxWidth / 2.0f
        }
    }

    private fun drawCoverStarWithoutMargin(canvas: Canvas, attributes: PasswordView) {
        val totalWidth = attributes.width
        val innerTotalWidth = totalWidth - attributes.boxStrokeWidth * (attributes.boxCount + 1)
        val avgWidth = innerTotalWidth / (attributes.boxCount * 1.0f)

        var left = attributes.boxStrokeWidth
        var startX: Float = left + avgWidth / 2.0f
        val cy = attributes.height / 2.0f
        val dx: Float = attributes.starPaint.measureText("*") / 2.0f
        attributes.starPaint.getTextBounds("*", 0, 1, attributes.rect)
        val dy: Float = (attributes.rect.top + attributes.rect.bottom) / 2.0f

        for (i in 0 until attributes.coverCount) {
            canvas.drawText("*", startX - dx, cy - dy, attributes.starPaint)
            left += avgWidth + attributes.boxStrokeWidth
            startX = left + avgWidth / 2.0f
        }
    }
}