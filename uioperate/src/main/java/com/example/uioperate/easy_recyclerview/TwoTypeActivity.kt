package com.example.uioperate.easy_recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.list.rados.fast_list.LayoutFactory
import com.list.rados.fast_list.bind

class TwoTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_type)

        val list = listOf(
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1),
            Item("recycler", 2),
            Item("view", 1),
        )

        findViewById<RecyclerView>(R.id.rv_content).bind(list)
            .map(
                layout = R.layout.recycler_item_easy,
                predicate = { it: Item, _ -> it.type == 1 }) { item: Item, p: Int ->
                findViewById<TextView>(R.id.tv_content).text = item.value
            }
            .map(
                layoutFactory = LocalFactory(this@TwoTypeActivity),
                predicate = { it: Item, _ -> it.type == 2 }) { item: Item, p: Int ->
                findViewById<TextView>(R.id.tv_content).text = item.value
            }
            .layoutManager(LinearLayoutManager(this))
    }

    class LocalFactory(val activity: AppCompatActivity) : LayoutFactory {
        override fun createView(parent: ViewGroup, type: Int): View {
            val view = LayoutInflater.from(activity).inflate(R.layout.recycler_item_easy_second, parent, false)
            return view
        }
    }
}