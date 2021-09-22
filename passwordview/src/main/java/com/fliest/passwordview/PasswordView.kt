package com.fliest.passwordview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

/*
    关闭复制粘贴功能
    屏蔽EditText select handle
    密码输入动效
 */
open class PasswordView(context: Context, private val attrs: AttributeSet) :
    NumberInputView(context, attrs) {

    companion object {
        private const val INPUT_STATE_DELETE = -1
        private const val INPUT_STATE_IDLE = 0
        private const val INPUT_STATE_INIT = 1
        private const val INPUT_STATE_ADD = 2
    }

    private var inputState = INPUT_STATE_INIT
    private var coverCount = 0

    private var dotRadius = 10f
    private var pwdCoverType = PWD_COVER_TYPE_DOT
    private var pwdCoverColor: Int = 0
    private var pwdCoverStarSize = 10f

    private lateinit var dotPaint: Paint
    private lateinit var starPaint: Paint
    private val delayTimeHandler = DelayTimeHandler()

    init {
        initAttrs()
        initEditText()
        initPaint()
    }

    private fun initAttrs() {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView)
        dotRadius =
            typedArray.getDimension(R.styleable.PasswordView_pwd_cover_dot_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, Resources.getSystem().displayMetrics))
        pwdCoverType =
            typedArray.getInt(R.styleable.PasswordView_pwd_cover_type, PWD_COVER_TYPE_DOT)
        pwdCoverColor =
            typedArray.getColor(R.styleable.PasswordView_pwd_cover_color, Color.parseColor("#BBBBBB"))
        pwdCoverStarSize =
            typedArray.getDimension(R.styleable.PasswordView_pwd_cover_star_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, Resources.getSystem().displayMetrics))
        typedArray.recycle()
    }

    private fun initEditText() {
        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = false

            override fun onDestroyActionMode(mode: ActionMode?) {}
        }
        setOnLongClickListener { true }
    }

    private fun initPaint() {
        when (pwdCoverType) {
            PWD_COVER_TYPE_DOT ->
                dotPaint = Paint().apply {
                    isAntiAlias = true
                    color = pwdCoverColor
                }

            PWD_COVER_TYPE_STAR ->
                starPaint = Paint().apply {
                    isAntiAlias = true
                    color = pwdCoverColor
                    textSize = pwdCoverStarSize
                }
        }
    }

    override fun onDrawText(canvas: Canvas, textPaint: Paint) {
        if (inputState != INPUT_STATE_INIT)
            drawPasswordCover(canvas, textPaint)

        if (inputState == INPUT_STATE_ADD)
            drawPlaintext(canvas, textPaint)
    }

    private fun drawPlaintext(canvas: Canvas, textPaint: Paint) {

        var left = 0f
        val height = height
        val cy = height / 2f

        val index = charList.size - 1
        val text = charList[index].toString()
        val dx: Float = textPaint.measureText(text) / 2
        val dy = (textPaint.fontMetrics.ascent + textPaint.fontMetrics.descent) / 2
        if (index != 0) left += (boxMargin + boxWidth) * index
        val startX: Float = left + boxWidth / 2

        canvas.drawText(text, startX - dx, cy - dy, textPaint)
    }

    private fun drawPasswordCover(canvas: Canvas, textPaint: Paint) {
        if (pwdCoverType == PWD_COVER_TYPE_DOT)
            drawCoverDot(canvas)
        else if (pwdCoverType == PWD_COVER_TYPE_STAR)
            drawCoverStar(canvas, textPaint)
    }

    private fun drawCoverDot(canvas: Canvas) {
        var left = boxStrokeWidth / 2
        val height = height
        val cy = height / 2f
        for (i in 0 until coverCount) {
            if (i != 0) left += (boxMargin + boxWidth)
            val startX = left + boxWidth / 2
            canvas.drawCircle(startX, cy, dotRadius, dotPaint)
        }
    }

    private fun drawCoverStar(canvas: Canvas, textPaint: Paint) {
        if (charList.size == 0) return

        var left = 0f
        var startX: Float = left + boxWidth / 2.0f
        val cy = height / 2.0f
        val dx: Float = starPaint.measureText("*") / 2.0f
        val dy = (starPaint.fontMetrics.ascent + starPaint.fontMetrics.descent) / 2.0f

        for (i in 0 until coverCount) {
            canvas.drawText("*", startX - dx, cy - dy, starPaint)
            left += boxMargin + boxWidth
            startX = left + boxWidth / 2.0f
        }
    }

    override fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
        val preNumListCount = charList.size
        val currNumListCount = s.length

        inputState =
            if (preNumListCount > currNumListCount) INPUT_STATE_DELETE else INPUT_STATE_ADD

        if (inputState == INPUT_STATE_ADD && coverCount < preNumListCount) {
            coverCount = charList.size
        }

        if (before > 0) charList.removeLast()
        if (count > 0) charList.add(s[start])

        if (inputState == INPUT_STATE_DELETE)
            coverCount = charList.size
        else
            delayTimeHandler.sendEmptyMessageDelayed(1, 1000)

        onInputFinishListener?.takeIf { currNumListCount == boxCount }?.onInputFinish(s.toString())
    }

    @SuppressLint("HandlerLeak")
    inner class DelayTimeHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            coverCount = charList.size
            inputState = INPUT_STATE_IDLE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        charList.clear()

        delayTimeHandler.removeCallbacksAndMessages(null)
    }

}