package com.example.uioperate.touch_event_gcssloop.boss_call_3

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.uioperate.touch_event_gcssloop.Static
import com.example.utilsgather.logcat.LogUtil

class Situation3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_situation3)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            LogUtil.d(Static.TAG1 + Static.dispatchTouchEvent + "现在项目做到什么程度了?")
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            LogUtil.d(Static.TAG1 + Static.onTouchEvent + "。。。")
        }
        return super.onTouchEvent(event)
    }
}