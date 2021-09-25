package com.fliest.passwordview

import android.graphics.Canvas
import android.graphics.Paint

/**
 * 绘制事件
 *
 * @author: fliest
 * @date: 2021-09-14 08:32:37
 */
interface InputViewDrawEvent {

    /**
     * 绘制边框
     */
    fun onDrawInputBox(canvas: Canvas, boxPaint: Paint)

    /**
     * 绘制文字
     */
    fun onDrawText(canvas: Canvas, textPaint: Paint)

    /**
     * 绘制光标
     */
    fun onDrawCursor(canvas: Canvas, cursorPaint: Paint)

    /**
     * 输入内容变化
     */
    fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int)
}