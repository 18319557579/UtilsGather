package com.example.uioperate.easy_recyclerview.self_optimize

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.uioperate.easy_recyclerview.Item

import com.example.uioperate.easy_recyclerview.self_optimize.bind

class OptimizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_type)

        val list = mutableListOf(
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1)
        )

        findViewById<RecyclerView>(R.id.rv_content).bind(
            list,
            R.layout.recycler_item_easy
        ) { it: Item, position: Int ->
            gainViewById<TextView>(R.id.tv_content).text = it.value
            gainViewById<TextView>(R.id.tv_index).text = it.type.toString()
        }

        findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            list.removeAt(1)
            list.add(Item("android", 1))
            list.add(Item("apple", 2))

            findViewById<RecyclerView>(R.id.rv_content).update(list)
        }
    }
}