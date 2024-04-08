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
            R.id.action_line_vertical_gravity_top -> {
                ofl.lineVerticalGravity = OptimizedFlowLayout.LINE_VERTICAL_GRAVITY_TOP
            }
            R.id.action_line_vertical_gravity_center_vertical -> {
                ofl.lineVerticalGravity = OptimizedFlowLayout.LINE_VERTICAL_GRAVITY_CENTER_VERTICAL
            }
            R.id.action_line_vertical_gravity_bottom -> {
                ofl.lineVerticalGravity = OptimizedFlowLayout.LINE_VERTICAL_GRAVITY_BOTTOM
            }

            //限制显示行数
            R.id.action_maxlines_0 -> {
                ofl.maxLines = 0
            }
            R.id.action_maxlines_1 -> {
                ofl.maxLines = 1
            }
            R.id.action_maxlines_3 -> {
                ofl.maxLines = 3
            }
            R.id.action_maxlines_maxCount_no_limit -> {
                ofl.maxLines = Int.MAX_VALUE
            }

            //限制显示个数
            R.id.action_maxCount_0 -> {
                ofl.maxCount = 0
            }
            R.id.action_maxCount_3 -> {
                ofl.maxCount = 3
            }
            R.id.action_maxCount_6 -> {
                ofl.maxCount = 6
            }
            R.id.action_maxlines_no_limit -> {
                ofl.maxCount = Int.MAX_VALUE
            }
        }
        return true
    }
}