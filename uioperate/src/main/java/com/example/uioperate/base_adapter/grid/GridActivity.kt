package com.example.uioperate.base_adapter.grid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.utilsgather.ui.SizeTransferUtil
import com.example.utilsgather.ui.toast.ToastManager

class GridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        val dataList = mutableListOf<String>().apply {
            repeat(100) {
                add("position ==> $it")
            }
        }

        findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = GridLayoutManager(this, 4)
            it.adapter = GridAdapter().apply {
                setDataList(dataList)
                setOnItemClickListener { item, _ ->
                    ToastManager.showToast(item)
                }
                setGridSpace(it, SizeTransferUtil.dip2px(10f, this@GridActivity))
            }
        }
    }
}