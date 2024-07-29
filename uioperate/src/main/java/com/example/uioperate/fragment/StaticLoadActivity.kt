package com.example.uioperate.fragment

import android.os.Bundle
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity

class StaticLoadActivity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static_load)
    }
}