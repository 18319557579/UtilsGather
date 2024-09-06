package com.example.uioperate.custom_juejin_newki.flow_layout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R

class AbstractNineGridLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abstract_nine_grid_layout)

        val imgList = listOf("1", "2", "3", "1", "2", "3", "1", "2", "3")
        val myNineGridAdapter = MyNineGridAdapter(imgList);

        findViewById<AbstractNineGridLayout>(R.id.abstract_nine_grid)
            .setAdapter(myNineGridAdapter)
    }
}