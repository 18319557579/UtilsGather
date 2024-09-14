package com.example.uioperate.base_adapter.staggered_grid

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.uioperate.R
import com.example.uioperate.base_adapter.grid.GridAdapter
import com.example.utilsgather.ui.SizeTransferUtil
import com.example.utilsgather.ui.toast.ToastManager
import java.util.Random

class StaggeredGridActivity : AppCompatActivity() {
    lateinit var gridAdapter: StaggeredGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staggered_grid)

        val dataList = mutableListOf<String>().apply {
            repeat(100) {
                add("position ==> $it")
            }
        }

        findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            it.setOverScrollMode(View.OVER_SCROLL_NEVER)
            it.adapter = StaggeredGridAdapter().apply {
                setDataList(dataList)
                setOnItemClickListener { item, _ ->
                    ToastManager.showToast(item)
                }
                setGridSpace(it, SizeTransferUtil.dip2px(10f, this@StaggeredGridActivity))
                gridAdapter = this
            }
        }

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val headLayout = LayoutInflater.from(this).inflate(R.layout.layout_head, null)
            val textView = headLayout.findViewById<TextView>(R.id.head_txt)
            val red = Random().nextInt(255)
            val green = Random().nextInt(255)
            val blue = Random().nextInt(255)
            textView.setBackgroundColor(Color.rgb(red, green, blue))
            gridAdapter.addHeadView(headLayout)
        }
        findViewById<Button>(R.id.btn_addfoot).setOnClickListener {
            val headLayout = LayoutInflater.from(this).inflate(R.layout.layout_foot, null)
            val textView = headLayout.findViewById<TextView>(R.id.head_txt)
            val red = Random().nextInt(255)
            val green = Random().nextInt(255)
            val blue = Random().nextInt(255)
            textView.setBackgroundColor(Color.rgb(red, green, blue))
            gridAdapter.addFootView(headLayout)
        }
        findViewById<Button>(R.id.btn_remove).setOnClickListener {
            gridAdapter.removeHeadView(0)
        }
        findViewById<Button>(R.id.btn_removefoot).setOnClickListener {
            gridAdapter.removeFootView(0);
        }
    }
}