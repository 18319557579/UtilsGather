package com.example.utilsuser

import android.os.Bundle
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.ui.immersion.ImmersionUtil

/**
 * 已经可以实现全沉浸式了
 */
class StyleImmersion2Activity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_immersion)

        ImmersionUtil.hideVirtualButton(this)

        ImmersionUtil.screenFull(this)
    }
}