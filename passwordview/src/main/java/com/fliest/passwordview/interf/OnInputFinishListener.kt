package com.fliest.passwordview.interf

/**
 * 输入完成监听
 * 当输入内容的长度达到指定的长度，会调用 onInputFinish()
 *
 * @author: fliest
 * @date: 2021-09-14 08:52:39
 */
interface OnInputFinishListener {
    fun onInputFinish(inputStr: String)
}