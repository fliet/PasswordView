package com.fliest.passwordview

class ReadMeTemp {
}

/*
    canvas.drawRect() 与 canvas.drawLine()
        使用这两个Api都可以绘制带有宽度的线段
        需要注意的是
            绘制完成后，我们设置的坐标会在线段宽度的中间
            比如，线段宽度为2，坐标为0，那么线段的宽度会占据从-1到1的空间
            也就是，我们设置的坐标是线段宽度的中间值，而不是起始值
*/

/*
    文字的居中
        文字的居中要考虑两方面：
            横向和纵向
        对于横向居中通过 Paint.measureText() / 2.0f 就可以实现
        对于纵向居中前后有两种方案

        旧方案
            val dy = (attributes.textPaint.fontMetrics.ascent + attributes.textPaint.fontMetrics.descent) / 2.0f
        新方案
            textPaint.getTextBounds("8",0,1,rect)
            val dy: Float = (rect.top + rect.bottom)/ 2.0f
        其实这两种方案对应着文字居中的两个方案
            基于文字实际占用的空间居中和基于文字实际显示的空间居中

            基于文字实际占用的空间居中
                这种居中方案，说是居中，但是并不是居中，因为文字上方和下方占用的空白区域的大小不同
                这也就导致了，通过 (fontMetrics.ascent + fontMetrics.descent) / 2.0f 或 (fontMetrics.top + fontMetrics.bottom) / 2.0f
                计算出来的值，不是文字的中间位置

                所以，这种方案的作用是哪个？
                    无边框下，为了保证输入任何字符后文字整体的纵坐标不发生变化，可以采取这种方案伪居中
                    有边框，立刻就能看出是不居中的
                    无论什么字符，其实际占用的空间是在一个固定的范围内的，所以采用这种方案，纵坐标不发生变化

            基于文字实际显示的空间居中
                用一个矩形包裹文字，那么这个矩形所占用的空间，就是文字实际显示的空间
                根据这个矩形将文字居中，才是真正的居中
 */

/*
    文字的实际占用空间与实际显示空间
        纵向
            实际占用空间
                fontMetrics.top + fontMetrics.bottom
            实际显示空间
                Paint.getTextBounds("8",0,1,rect)
                rect.top + rect.bottom

        横向
            实际占用空间
                float textWidth = paint.measureText(text);
            实际显示空间
                Paint.getTextBounds("8",0,1,rect)
                rect.left + rect.right

        横向不会像纵向一样，横向的实际占用空间作用两侧占用的空间是均匀的

        计算横向中间值的时候，不能使用实际显示空间
        因为横坐标的原点不是文字绘制的起始位置，二者之间是有间隔的
        如果使用这个方案的话，会造成文字偏向左侧，因为横坐标原点到文字绘制的起始位置之间的距离没有考虑进去
 */

/*
    纵向居中使用 文字实际显示空间居中
    横向居中使用 文字实际实际空间居中
 */

/*
    关闭复制粘贴功能
    屏蔽EditText select handle
    密码输入动效
 */

/*
    EditText
        关闭长按后显示的图标
                android:textSelectHandle="@drawable/shape_text_select_handle"
                android:textSelectHandleLeft="@drawable/shape_text_select_handle"
                android:textSelectHandleRight="@drawable/shape_text_select_handle"
        关闭长按粘贴
                上面的内容
                customSelectionActionModeCallback = object : ActionMode.Callback {
                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = false

                    override fun onDestroyActionMode(mode: ActionMode?) {}
                }
                setOnLongClickListener { true }

 */

/*
    测量与绘制
        测量
           高度为 box_height
           宽度为 box_width * box_count + boxMargin * (boxCount - 1)
        绘制
           因为 canvas.drawRect() 与 canvas.drawLine() 绘制时坐标与宽度的问题
           为了保证所有内容都绘制在指定范围内
           需要将边框绘制的坐标内移 box_stroke_width / 2.0f

        我们的目标是将所有内容都绘制在
            高度为 box_height
            宽度为 box_width * box_count + boxMargin * (boxCount - 1)
        的范围内，所以输入框的边框就需要向输入框内画，边框的宽度需要向输入框内延伸
        也就是边框的宽度会挤压输入框的内部空间，不会挤压输入框之间的空间
        所以，就需要按照上面的方案进行测量与绘制
 */

/*
    无边距情况
       无边距情况下，两个输入框之间的边框是重叠的
       这个时候就不能按照有间距情况计算与绘制边框、文字和光标

       边框
          绘制的思路
             和有边距的单独绘制不同，无边距情况下，会先绘制一个长矩形，然后在该矩形内部绘制线段，将矩形分割成大小统一的边框

          计算边框的思路是：计算每个边框需要的空白空间
              （矩形的长度 - 矩形左右两边的宽度 - 矩形内每个分割线的宽度）/ boxCount
              根据边框线（分割线）的宽度和空白空间的宽度，计算每个分割线的startX

        文字与光标是类似的处理方式
 */

/*
    遇到的问题
        EditText自带的长按效果

        文字、光标的居中

        无边距情况下，边框、文字和光标的计算与绘制

        密码的显示隐藏

        后续的扩展
 */

/*
    可拓展性
        对使用者
            BaseInputView中实现了数字输入框的基本功能，但是使用者想实现自己的功能怎么办
            InputViewDrawEvent中定义了用于绘制的方法，只要继承BaseInputView，并重写这些方法就可以实现自定义
            PasswordView就是一个示例
            当然，绘制时需要的坐标信息需要你自己去计算，这就要求你对BaseInputView的绘制思路有一定的了解

        对开发者
            每当想增加一个新的样式，就需要在BaseInputView的onDraw()中添加代码
            随着样式的增多，大量代码堆砌、糅合在一个类中，这无疑降低了代码的可读性与可拓展性

            外观模式
                将每个样式都封装成一个单独的类，类中暴露出绘制该样式的接口
                在View的onDraw()中调用每个样式的绘制接口
                这样，实现了子样式与View的分离，对后续样式的增加和删除提供了方便
                开发者就可以随意的增加和删除样式了
 */

