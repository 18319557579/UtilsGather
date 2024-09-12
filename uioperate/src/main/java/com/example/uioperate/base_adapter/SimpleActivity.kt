package com.example.uioperate.base_adapter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R

class SimpleActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        init()
    }

    private fun init() {
        recyclerView = findViewById(R.id.recyclerView)

        val dataList = mutableListOf<String>().apply {
            repeat(30) {
                add(it.toString())
            }
        }

        val simpleAdapter = SimpleAdapter().apply {
            setDataList(dataList)
            setOnItemClickListener { item, position ->

            }
        }
        recyclerView.adapter = simpleAdapter
    }
}