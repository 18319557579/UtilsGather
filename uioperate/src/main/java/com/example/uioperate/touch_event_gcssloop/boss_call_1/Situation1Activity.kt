package com.example.uioperate.touch_event_gcssloop.boss_call_1

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.touch_event_gcssloop.Static
import com.example.utilsgather.logcat.LogUtil

class Situation1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_situation_1)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            LogUtil.d(Static.TAG1 + Static.dispatchTouchEvent + "经理,我准备发展一下电商业务,下周之前做一个淘宝出来.")
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            LogUtil.d(Static.TAG1 + Static.onTouchEvent + "这么简单都做不了,你们都是干啥的(愤怒).")
        }
        return super.onTouchEvent(event)
    }
}