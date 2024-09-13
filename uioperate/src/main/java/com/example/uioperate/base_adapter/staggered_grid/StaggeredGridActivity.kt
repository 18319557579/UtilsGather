package com.example.uioperate.base_adapter.staggered_grid

import android.os.Bundle
import android.view.View
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
            it.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
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
    }
}