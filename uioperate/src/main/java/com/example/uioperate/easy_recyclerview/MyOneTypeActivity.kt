package com.example.uioperate.easy_recyclerview

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R

class MyOneTypeActivity : AppCompatActivity() {
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
            Item("view", 1), Item("fast", 1),
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
            Item("view", 1), Item("fast", 1),
            Item("recycler", 2),
            Item("view", 1)
        )

        /*findViewById<RecyclerView>(R.id.rv_content).bind(
            list,
            R.layout.recycler_item_easy,
            { container: View, viewType: Int ->
                MyHolder(container, viewType)
            },
            { item: Item, position: Int ->
                if (this is MyHolder) {
                    this.title.text = item.value
                    this.index.text = item.type.toString()
                }
            }
        )*/

        findViewById<RecyclerView>(R.id.rv_content).bind(
            list,
            R.layout.recycler_item_easy,
            { container: View, viewType: Int ->
                MyHolder(container, viewType)
            },
        )

        findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            list.removeAt(1)
            list.add(Item("android", 1))
            list.add(Item("apple", 2))

            findViewById<RecyclerView>(R.id.rv_content).update(list)
        }
    }

    class MyHolder(container: View, viewType: Int) : FastListViewHolder<Item>(container, viewType) {
        val title: TextView = container.findViewById(R.id.tv_content)
        val index: TextView = container.findViewById(R.id.tv_index)

        override fun bind(entry: Item, position: Int) {
            title.text = entry.value
            index.text = entry.type.toString()
        }
    }
}