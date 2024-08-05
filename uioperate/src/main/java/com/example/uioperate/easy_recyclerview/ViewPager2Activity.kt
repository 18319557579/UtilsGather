package com.example.uioperate.easy_recyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.uioperate.R

import com.list.rados.fast_list.bind

class ViewPager2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

        val list = mutableListOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        findViewById<ViewPager2>(R.id.vp_content).bind(list, R.layout.viewpager_item_easy) { it : Item, position: Int ->
            findViewById<TextView>(R.id.tv_content).text = it.value
        }
    }
}