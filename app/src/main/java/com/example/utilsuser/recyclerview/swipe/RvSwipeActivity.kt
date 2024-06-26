package com.example.utilsuser.recyclerview.swipe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utilsuser.R
import kotlin.random.Random

class RvSwipeActivity : AppCompatActivity() {
    lateinit var rvSlideDelete : RecyclerView
    val webList = ArrayList<SwipeInfoBean>()
    lateinit var slideDeleteAdapter: SwipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_swipe)

        initView()
        initData()
    }

    private fun initView() {
        rvSlideDelete = findViewById<RecyclerView?>(R.id.rv_slide_delete).apply {
            val linearLayoutManager = LinearLayoutManager(this@RvSwipeActivity)
            layoutManager = linearLayoutManager

            slideDeleteAdapter = SwipeAdapter(webList, this@RvSwipeActivity)
            adapter = slideDeleteAdapter

            val callback = SwipeTouchHelperCallback(slideDeleteAdapter)
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun initData() {
        repeat(5) {
            val bean = SwipeInfoBean("这个是网页$it, 请打开浏览享受精彩")
            webList.add(bean)
        }
        slideDeleteAdapter.notifyDataSetChanged()
    }

    fun addItem(view: View) {
        val randomInt = Random.Default.nextInt(10, 100)
        val bean = SwipeInfoBean("这个是网页$randomInt, 请打开浏览享受精彩")
        webList.add(bean)
        slideDeleteAdapter.notifyItemInserted(webList.size - 1)
    }
}