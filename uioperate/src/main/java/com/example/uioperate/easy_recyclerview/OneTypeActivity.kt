package com.example.uioperate.easy_recyclerview

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.list.rados.fast_list.bind
import com.list.rados.fast_list.update

class OneTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_type)

        val list = mutableListOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        findViewById<RecyclerView>(R.id.rv_content).bind(list, R.layout.recycler_item_easy) { it : Item, position: Int ->
            findViewById<TextView>(R.id.tv_content).text = it.value
        }

        findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            list.removeAt(1)
            list.add(Item("android", 1))
            list.add(Item("apple", 2))

            findViewById<RecyclerView>(R.id.rv_content).update(list)
        }
    }
}