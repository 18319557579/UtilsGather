package com.example.utilsuser.gesture

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsuser.R

class MyWindowManagerActivity : AppCompatActivity(), View.OnTouchListener {
    lateinit var mWindowManager: WindowManager
    lateinit var mImageView: ImageView
    lateinit var mLayoutParams: WindowManager.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_manager)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (! Settings.canDrawOverlays(this)) {
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                startActivityForResult(myIntent, 100)
            } else {
                initView()
            }

        } else {
            initView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            initView()
        }
    }

    private fun initView() {
        findViewById<Button>(R.id.add_btn).setOnClickListener {
            mImageView = ImageView(this@MyWindowManagerActivity)
            mImageView.setBackgroundResource(R.mipmap.ic_launcher)

            mLayoutParams = WindowManager.LayoutParams (
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                2099,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSPARENT
            )
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            mLayoutParams.gravity = Gravity.TOP or Gravity.LEFT
            mLayoutParams.x = 0
            mLayoutParams.y = 300
            mImageView.setOnTouchListener(this)
            mWindowManager.addView(mImageView, mLayoutParams)
        }
        findViewById<Button>(R.id.rmv_btn).setOnClickListener {
            mWindowManager.removeViewImmediate(mImageView)
        }
        mWindowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val rawX = event!!.rawX.toInt()
        val rawY = event!!.rawY.toInt()

        when(event.action) {
            MotionEvent.ACTION_MOVE -> {
                mLayoutParams.x = rawX
                mLayoutParams.y = rawY
                mWindowManager.updateViewLayout(mImageView, mLayoutParams)
            }
        }
        return false
    }
}