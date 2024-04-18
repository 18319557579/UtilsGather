package com.example.utilsuser.qihang.one.RectPointView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Region
import android.util.AttributeSet
import android.view.View
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class ClipRgnView : View {
    private lateinit var mBitmap: Bitmap
    private var clipWidth: Int = 0
    private var width = 0
    private var height = 0
    private val CLIP_HEIGHT = 30
    private lateinit var mRgn: Region

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.scenery)
        width = mBitmap.width
        height = mBitmap.height
        mRgn = Region()
    }

    override fun onDraw(canvas: Canvas) {
        LogUtil.d("调用了onDraw(): clipWidth: $clipWidth")
        mRgn.setEmpty()
        var i = 0
        while (i * CLIP_HEIGHT <= height) {  //计算clip的区域，从上到下，一级一级
            if (i % 2 == 0) {
                mRgn.union(Rect(0, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT))
            } else {
                mRgn.union(Rect(width - clipWidth, i * CLIP_HEIGHT, width, (i + 1) * CLIP_HEIGHT))
            }
            i++
        }
        //todo clipRegion过时了，kotlin这里压根调不到，用clipRect进行替换。可能是这个原因导致的没有出效果
        canvas.clipRect(mRgn.getBounds())
        canvas.drawBitmap(mBitmap, 0f, 0f, Paint())

        if (clipWidth > width) {
            return
        }
        clipWidth += 5

        invalidate()
    }
}