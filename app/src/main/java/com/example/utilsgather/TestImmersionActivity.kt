package com.example.utilsgather

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import com.example.utilsgather.lifecycle_callback.CallbackActivity
import com.example.utilsgather.ui.immersion.ImmersionUtil

class TestImmersionActivity : CallbackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        findViewById<Button>(R.id.btn_hide_virtual_button).setOnClickListener {
            ImmersionUtil.hideVirtualButton(this)
        }
        findViewById<Button>(R.id.btn_set_screen_full).setOnClickListener {
            ImmersionUtil.screenFull(this)
        }
    }
}