package com.example.utilsgather

import android.os.Bundle
import com.example.utilsgather.exit.ExitHandleActivity

class TextExitHandleActivity : ExitHandleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_exit_handle)
    }
}