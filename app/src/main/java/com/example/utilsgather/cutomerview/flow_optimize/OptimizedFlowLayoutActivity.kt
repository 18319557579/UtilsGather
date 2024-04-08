package com.example.utilsgather.cutomerview.flow_optimize

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.R

class OptimizedFlowLayoutActivity : AppCompatActivity() {
    lateinit var ofl: OptimizedFlowLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_flow_optimized)
        ofl = findViewById<OptimizedFlowLayout>(R.id.ofl)
    }

    fun addView(view: View?) {
        ofl.addView(
            TextView(this).apply {
                text = "RxJava"
                setBackgroundResource(R.drawable.shape_button_circular)
                setPadding(12.dp2px(), 2.dp2px(), 12.dp2px(), 2.dp2px())
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            }
        )
    }
}