package com.example.uioperate.base_adapter.item_touch.custom

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.uioperate.base_adapter.item_touch.ItemEntity
import com.example.uioperate.base_adapter.item_touch.ItemTouchManager
import com.example.uioperate.base_adapter.item_touch.JobOrientationAdapter

class ItemTouchCustomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_touch)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = LinearLayoutManager(this)
            it.setOverScrollMode(View.OVER_SCROLL_NEVER)
        }

        val adapter = CustomAdapter().apply {
            val mList = ArrayList<ItemEntity>()
            repeat(5){
                val strings = arrayOf(
                    "Android",
                    "后端",
                    "前端",
                    "iOS",
                    "人工智能",
                    "产品",
                    "工具资源",
                    "阅读",
                    "设计"
                )
                for (string in strings) {
                    val item = ItemEntity()
                    item.isChecked = false
                    item.text = string
                    mList.add(item)
                }
            }

            setDataList(mList)
        }
        recyclerView.adapter = adapter

        ItemTouchManager.attachTo(recyclerView, ItemTouchHelper.UP, ItemTouchHelper.LEFT)
    }
}