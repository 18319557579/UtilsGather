package com.example.utilsuser.qihang.seven

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsuser.R

class ShaowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shaow)

        findViewById<TextView>(R.id.tv_china).setShadowLayer(2f, 5f, 5f, Color.GRAY)
    }

    fun changeShadow(view: View) {
        val shadowView = findViewById<ShadowView>(R.id.sv)
        shadowView.setShadow(!shadowView.mSetShow)
    }
}