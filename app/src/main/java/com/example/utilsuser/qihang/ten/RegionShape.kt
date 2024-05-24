package com.example.utilsuser.qihang.ten

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Region
import android.graphics.RegionIterator
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet

class RegionShape(region: Region) : Shape() {

    private val mRegion: Region = region

    override fun draw(canvas: Canvas, paint: Paint) {
        val iter = RegionIterator(mRegion)
        val r = Rect()

        while(iter.next(r)) {
            canvas.drawRect(r, paint)
        }
    }
}