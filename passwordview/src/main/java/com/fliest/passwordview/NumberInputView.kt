package com.fliest.passwordview

import android.content.Context
import android.util.AttributeSet
import com.fliest.passwordview.base.BaseInputView

/**
 * 数字输入框
 * 可用作验证码输入框
 *
 * @author: fliest
 * @date: 2021-09-14 08:52:31
 */
open class NumberInputView : BaseInputView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}