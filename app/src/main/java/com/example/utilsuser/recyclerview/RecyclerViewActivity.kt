package com.example.utilsuser.recyclerview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utilsuser.R
import com.example.utilsuser.recyclerview.rv.MyItemTouchHelperCallback
import com.example.utilsuser.recyclerview.rv.SlideDeleteAdapter
import com.example.utilsuser.recyclerview.rv.WebInfoBean
import kotlin.random.Random

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var rvSlideDelete : RecyclerView
    val webList = ArrayList<WebInfoBean>()
    lateinit var slideDeleteAdapter: SlideDeleteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initView()
        initData()
    }

    private fun initView() {
        rvSlideDelete = findViewById<RecyclerView?>(R.id.rv_slide_delete).apply {
            val linearLayoutManager = LinearLayoutManager(this@RecyclerViewActivity)
            layoutManager = linearLayoutManager

            slideDeleteAdapter = SlideDeleteAdapter(webList, this@RecyclerViewActivity)
            adapter = slideDeleteAdapter

            val callback = MyItemTouchHelperCallback(slideDeleteAdapter)
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun initData() {
        repeat(5) {
            val bean = WebInfoBean("这个是网页$it, 请打开浏览享受精彩")
            webList.add(bean)
        }
        slideDeleteAdapter.notifyDataSetChanged()
    }

    fun addItem(view: View) {
        val randomInt = Random.Default.nextInt(10, 100)
        val bean = WebInfoBean("这个是网页$randomInt, 请打开浏览享受精彩")
        webList.add(bean)
        slideDeleteAdapter.notifyItemInserted(webList.size - 1)
    }
}