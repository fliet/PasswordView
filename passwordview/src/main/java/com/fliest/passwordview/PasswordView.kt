package com.fliest.passwordview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.fliest.passwordview.base.ITextView
import com.fliest.passwordview.textview.PasswordCoverTextView
import com.fliest.passwordview.textview.PlaintTextView

/**
 * 密码输入框
 * 自定义输入框示例
 *
 * @author: fliest
 * @date: 2021-09-14 08:38:03
 */
open class PasswordView(context: Context, private val attrs: AttributeSet) :
    NumberInputView(context, attrs) {

    companion object {
        const val INPUT_STATE_DELETE = -1
        const val INPUT_STATE_IDLE = 0
        const val INPUT_STATE_INIT = 1
        const val INPUT_STATE_ADD = 2
    }

    var inputState = INPUT_STATE_INIT
    var coverCount = 0

    var dotRadius = 10f
    var pwdCoverType = PWD_COVER_TYPE_DOT
    var pwdCoverColor: Int = 0
    var pwdCoverStarSize = 10f

    lateinit var dotPaint: Paint
    lateinit var starPaint: Paint

    val rect = Rect()
    private val delayTimeHandler = DelayTimeHandler()
    private val plaintTextView: ITextView = PlaintTextView()
    private val passwordCoverTextView: ITextView = PasswordCoverTextView()

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
            passwordCoverTextView.drawText(canvas, this)

        if (inputState == INPUT_STATE_ADD)
            plaintTextView.drawText(canvas, this)
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        charList.clear()

        delayTimeHandler.removeCallbacksAndMessages(null)
    }

    @SuppressLint("HandlerLeak")
    inner class DelayTimeHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            coverCount = charList.size
            inputState = INPUT_STATE_IDLE
        }
    }

}