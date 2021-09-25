package com.fliest.passwordview.base

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.fliest.passwordview.*
import com.fliest.passwordview.cursor.BoxCursorView
import com.fliest.passwordview.cursor.DefaultCursorView
import com.fliest.passwordview.cursor.FillBoxCursorView
import com.fliest.passwordview.cursor.UnderlineCursorView
import java.util.*

/**
 * 输入框基类
 *
 * @author: fliest
 * @date: 2021-09-14 08:38:03
 */
open class BaseInputView : AppCompatEditText, InputViewDrawEvent {
    companion object {
        // 默认数量
        const val DEFAULT_INPUT_COUNT = 4

        // 填充输入框
        const val BOX_STYLE_TYPE_FILL = 1

        // 描边输入框
        const val BOX_STYLE_TYPE_STROKE = 2

        // 下划线输入框
        const val BOX_STYLE_TYPE_UNDERLINE = 3

        // 密码覆盖物 - 圆点
        const val PWD_COVER_TYPE_DOT = 1

        // 密码覆盖物 - *
        const val PWD_COVER_TYPE_STAR = 2

        // 光标样式 - 竖线
        const val CURSOR_TYPE_LINE = 1

        // 光标颜色 - 跟随输入框
        const val CURSOR_TYPE_FOLLOW_BOX = 2
    }

    // 输入框数量
    var boxCount: Int = DEFAULT_INPUT_COUNT

    // 输入框宽度
    var boxWidth: Float = 0f

    // 输入框高度
    var boxHeight: Float = 0f

    // 输入框间隔
    var boxMargin: Float = 0f

    // 输入框样式类型
    var boxStyleType: Int = BOX_STYLE_TYPE_FILL

    // 输入框填充颜色
    var boxFillColor: Int = 0

    // 输入框描边颜色
    var boxStrokeColor: Int = 0

    // 输入框描边宽度
    var boxStrokeWidth: Float = 0f

    // 输入框下划线颜色
    var boxUnderlineColor: Int = 0

    // 输入框下划线高度
    var boxUnderlineHeight: Float = 0f

    // 输入框圆角
    var boxCorner: Float = 0f

    // 文字颜色
    var inputTextColor: Int = 0

    // 文字尺寸
    var inputTextSize: Float = 0f

    /*
        光标类型
        // 光标样式 - 竖线
        const val CURSOR_TYPE_LINE = 1
        // 光标颜色 - 跟随输入框
        const val CURSOR_TYPE_FOLLOW_BOX = 2

        如果是CURSOR_TYPE_FOLLOW_BOX，那么光标的样式，宽度会跟随输入框
        光标的颜色可以通过 cursor_color 进行设定
     */
    var cursorType: Int = CURSOR_TYPE_LINE

    // 光标高度
    var cursorHeight: Float = 0f

    // 光标宽度
    var cursorWidth: Float = 0f

    // 光标颜色
    var cursorColor: Int = 0

    // 光标透明度
    private var cursorAlpha = 0

    // 光标是否闪烁
    private var cursorTwinkle = false

    internal val boxPaint = Paint()
    internal val textPaint = Paint()
    internal val cursorPaint = Paint()

    private val cursorViewList =
        listOf(DefaultCursorView(), BoxCursorView(), UnderlineCursorView(), FillBoxCursorView())
    private val defaultBoxView: BaseBoxView = DefaultBoxView()
    private val defaultTextView: BaseTextView = DefaultTextView()

    internal val charList: MutableList<Char> = ArrayList()
    var onInputFinishListener: OnInputFinishListener? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        initAttrs(context, attrs)
        initEditText()
        initPaint()
        initAnimation()
    }

    /**
     * 初始化属性值
     */
    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseInputView)

        boxCount = typedArray.getInt(R.styleable.BaseInputView_box_count, DEFAULT_INPUT_COUNT)
        boxWidth =
            typedArray.getDimension(R.styleable.BaseInputView_box_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48f, Resources.getSystem().displayMetrics))
        boxHeight =
            typedArray.getDimension(R.styleable.BaseInputView_box_height, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48f, Resources.getSystem().displayMetrics))
        boxMargin =
            typedArray.getDimension(R.styleable.BaseInputView_box_margin, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, Resources.getSystem().displayMetrics))
        boxFillColor =
            typedArray.getColor(R.styleable.BaseInputView_box_fill_color, Color.parseColor("#BBBBBB"))
        boxStrokeColor =
            typedArray.getColor(R.styleable.BaseInputView_box_stroke_color, Color.parseColor("#BBBBBB"))
        boxCorner =
            typedArray.getDimension(R.styleable.BaseInputView_box_corner, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, Resources.getSystem().displayMetrics))
        boxStyleType =
            typedArray.getInt(R.styleable.BaseInputView_box_style_type, BOX_STYLE_TYPE_FILL)
        boxStrokeWidth =
            typedArray.getDimension(R.styleable.BaseInputView_box_stroke_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, Resources.getSystem().displayMetrics))
        boxUnderlineColor =
            typedArray.getColor(R.styleable.BaseInputView_box_underline_color, Color.parseColor("#BBBBBB"))
        boxUnderlineHeight =
            typedArray.getDimension(R.styleable.BaseInputView_box_underline_height, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, Resources.getSystem().displayMetrics))
        inputTextColor =
            typedArray.getColor(R.styleable.BaseInputView_input_text_color, Color.parseColor("#BBBBBB"))
        inputTextSize =
            typedArray.getDimension(R.styleable.BaseInputView_input_text_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1212f, Resources.getSystem().displayMetrics))

        cursorType =
            typedArray.getInt(R.styleable.BaseInputView_cursor_type, CURSOR_TYPE_LINE)
        cursorHeight =
            typedArray.getDimension(R.styleable.BaseInputView_cursor_height, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, Resources.getSystem().displayMetrics))
        cursorWidth =
            typedArray.getDimension(R.styleable.BaseInputView_cursor_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, Resources.getSystem().displayMetrics))
        cursorColor =
            typedArray.getColor(R.styleable.BaseInputView_cursor_color, Color.parseColor("#BBBBBB"))
        cursorTwinkle =
            typedArray.getBoolean(R.styleable.BaseInputView_cursor_twinkle, false)

        typedArray.recycle()

        // 非边框模式下，边框宽度为0
        if (boxStyleType != BOX_STYLE_TYPE_STROKE)
            boxStrokeWidth = 0f
    }

    /**
     * 初始化EditText
     */
    private fun initEditText() {
        isFocusable = true
        isFocusableInTouchMode = true

        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        background = null
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(boxCount))

        setTextColor(ContextCompat.getColor(context, android.R.color.transparent))
        addTextChangedListener(object : TextWatcherImpl() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                onTextChange(s, start, before, count)
            }
        })
    }

    /**
     * 初始化Paint
     */
    private fun initPaint() {
        boxPaint.isAntiAlias = true

        when (boxStyleType) {
            BOX_STYLE_TYPE_FILL -> {
                boxPaint.color = boxFillColor
                boxPaint.style = Paint.Style.FILL
            }

            BOX_STYLE_TYPE_STROKE -> {
                boxPaint.color = boxStrokeColor
                boxPaint.style = Paint.Style.STROKE
                boxPaint.strokeWidth = boxStrokeWidth
            }

            BOX_STYLE_TYPE_UNDERLINE -> {
                boxPaint.color = boxUnderlineColor
                boxPaint.style = Paint.Style.FILL
                boxPaint.strokeWidth = boxUnderlineHeight
            }
        }

        textPaint.isAntiAlias = true
        textPaint.color = inputTextColor
        textPaint.textSize = inputTextSize

        cursorPaint.isAntiAlias = true
        cursorPaint.color = cursorColor
        cursorPaint.strokeWidth = cursorWidth
        cursorPaint.style =
            if (cursorType == CURSOR_TYPE_LINE) // 竖线
                Paint.Style.FILL
            else { // 跟随边框
                cursorPaint.strokeWidth = boxPaint.strokeWidth
                if (boxStyleType == BOX_STYLE_TYPE_STROKE) {
                    Paint.Style.STROKE
                } else
                    Paint.Style.FILL
            }
    }

    /**
     * 设置光标闪烁动画
     */
    private fun initAnimation() {
        if (cursorHeight == 0f || cursorWidth == 0f)
            return

        if (cursorTwinkle)
            ObjectAnimator
                .ofInt(this, "cursorAlpha", 255, 0)
                .apply {
                    duration = 1000
                    repeatMode = ValueAnimator.RESTART
                    repeatCount = ValueAnimator.INFINITE
                }
                .start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize: Int = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize: Int = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode: Int = MeasureSpec.getMode(heightMeasureSpec)

        val measuredWidth =
            if (widthMode == MeasureSpec.EXACTLY) widthSize
            else (boxWidth * boxCount + boxMargin * (boxCount - 1)).toInt()

        val measuredHeight =
            if (heightMode == MeasureSpec.EXACTLY) heightSize
            else boxHeight.toInt()

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        onDrawInputBox(canvas, boxPaint)
        onDrawText(canvas, textPaint)
        onDrawCursor(canvas, cursorPaint)
    }

    override fun onDrawInputBox(canvas: Canvas, boxPaint: Paint) {
        defaultBoxView.drawBox(canvas, this)
    }

    override fun onDrawText(canvas: Canvas, textPaint: Paint) {
        defaultTextView.drawText(canvas, this)
    }

    override fun onDrawCursor(canvas: Canvas, cursorPaint: Paint) {
        cursorViewList.forEach { it.drawCursor(canvas, this) }
    }

    override fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
        if (before > 0) charList.removeLast()
        if (count > 0)
            for (i in start until start + count)
                charList.add(s[i])

        if (charList.size == boxCount) {
            val stringBuilder = StringBuilder()
            charList.forEach {
                stringBuilder.append(it)
            }

            onInputFinishListener?.onInputFinish(stringBuilder.toString())
        }
    }

    fun setCursorAlpha(cursorAlpha: Int) {
        this.cursorAlpha = cursorAlpha
        cursorPaint.alpha = cursorAlpha

        invalidate()
    }

    fun getCursorAlpha() = cursorAlpha
}