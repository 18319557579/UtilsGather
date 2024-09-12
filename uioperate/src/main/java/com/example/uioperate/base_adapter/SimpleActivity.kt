package com.example.uioperate.base_adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.toast.ToastManager

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
                ToastManager.showToast(item)
            }
        }
        recyclerView.adapter = simpleAdapter
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d("回调onPause")
        ToastManager.cancelAllToast()
    }
}