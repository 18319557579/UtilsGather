package com.example.utilsuser.gesture

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import kotlin.math.abs

class GestureActivity : AppCompatActivity() , OnTouchListener{
    val mGestureDetector : GestureDetector by lazy {
        /*GestureDetector(gestureListener()).apply {
            setOnDoubleTapListener(doubleTapListener())
        }*/
        GestureDetector(simpleGestureListener())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)

        findViewById<TextView>(R.id.tv_center_content).apply {
            setOnTouchListener(this@GestureActivity)
            isFocusable = true
            isClickable = true
            isLongClickable = true
        }

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(event !!)
    }

    class gestureListener : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent): Boolean {
            LogUtil.d("MyGesture回调 onDown")
            return false
        }

        override fun onShowPress(e: MotionEvent) {
            LogUtil.d("MyGesture回调 onShowPress")
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            LogUtil.d("MyGesture回调 onSingleTapUp")
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            LogUtil.d("MyGesture回调 onScroll: ${e2.x - e1?.x!!} ${distanceX}")
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            LogUtil.d("MyGesture回调 onLongPress")
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            LogUtil.d("MyGesture回调 onFling")
            return true
        }

    }

    class doubleTapListener : GestureDetector.OnDoubleTapListener {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            LogUtil.d("MyGesture回调 onSingleTapConfirmed")
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            LogUtil.d("MyGesture回调 onDoubleTap")
            return true
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            LogUtil.d("MyGesture回调 onDoubleTapEvent ${e.action}")
            return true
        }

    }

    class simpleGestureListener : GestureDetector.SimpleOnGestureListener() {
        val FLING_MIN_DISTANCE = 100
        val FLING_MIN_VELOCITY = 200

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1!!.x - e2.x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                LogUtil.d("MyGesture回调 Fling Left")
            } else if (e2.x - e1.x > FLING_MIN_DISTANCE && abs(velocityX) > FLING_MIN_VELOCITY){
                LogUtil.d("MyGesture回调 Fling Right")
            }

            return true
        }
    }
}