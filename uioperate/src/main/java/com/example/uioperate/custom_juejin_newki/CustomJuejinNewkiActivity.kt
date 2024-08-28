package com.example.uioperate.custom_juejin_newki

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R

class CustomJuejinNewkiActivity : AppCompatActivity() {
    lateinit var btnSetProgress: Button
    lateinit var myCircleProgressView: MyCircleProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_juejin_newki)

        btnSetProgress = findViewById(R.id.btn_set_progress)
        myCircleProgressView = findViewById(R.id.mcpv)

        btnSetProgress.setOnClickListener {
            myCircleProgressView.setupValue(60, 100)
        }
    }
}