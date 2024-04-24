package com.example.utilsuser.qihang.one.six

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.utilsgather.logcat.LogUtil

class CustomView : View {
    constructor(context: Context?) : super(context) {
        LogUtil.d("调用了 context 参数的构造函数")
    }

    /*如果要通过XML引入控件，就必须实现这个函数，否则会报错*/
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LogUtil.d("调用了 context, attrs 参数的构造函数")
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LogUtil.d("调用了 context, attrs, defStyleAttr 参数的构造函数")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        Paint().let {
            it.setColor(Color.RED)
            canvas.drawRect(0f, 0f, 200f, 100f, it)
        }
    }

}