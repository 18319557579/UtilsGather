package com.example.utilsgather.cutomerview.flow_optimize

import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_item -> {
                ofl.addView(
                    TextView(this).apply {
                        text = "RxJava"
                        setBackgroundResource(R.drawable.shape_button_circular)
                        setPadding(12.dp2px(), 2.dp2px(), 12.dp2px(), 2.dp2px())
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                    }
                )
            }
            R.id.action_add_item_a -> {
                ofl.addView(
                    TextView(this).apply {
                        text = "RxJava"
                        setBackgroundResource(R.drawable.shape_button_circular)
                        setPadding(12.dp2px(), 2.dp2px(), 12.dp2px(), 2.dp2px())
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    }
                )
            }
        }
        return true
    }
}