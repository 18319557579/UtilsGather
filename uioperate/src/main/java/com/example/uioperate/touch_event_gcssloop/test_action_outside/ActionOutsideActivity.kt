package com.example.uioperate.touch_event_gcssloop.test_action_outside


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.logcat.LogUtil

// todo 本来是测试 ACTION_OUTSIDE 事件的，但是始终没有测出来
class ActionOutsideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_outside)

        findViewById<Button>(R.id.btn_show).setOnClickListener { v->
            showPopupWindow(v)
        }
        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {

        }
    }

    private fun showPopupWindow(anchor: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.parseColor("#800000ff")))
        popupWindow.setTouchInterceptor { v, event ->
            LogUtil.d("回调了 setTouchInterceptor: $event")
            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                Toast.makeText(this@ActionOutsideActivity, "Outside touch event received", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                true
            } else {
                false
            }
        }
        popupWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
    }
}