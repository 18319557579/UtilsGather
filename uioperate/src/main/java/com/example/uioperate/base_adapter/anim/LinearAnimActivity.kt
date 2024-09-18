package com.example.uioperate.base_adapter.anim

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.uioperate.base_adapter.AnimationType
import com.example.uioperate.base_adapter.simple.SimpleAdapter
import com.example.utilsgather.ui.SizeTransferUtil
import com.example.utilsgather.ui.toast.ToastManager
import java.lang.RuntimeException

class LinearAnimActivity : AppCompatActivity() {
    lateinit var mAdapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_anim)

        init();
    }

    private fun init() {
        val dataList = mutableListOf<String>().apply {
            repeat(100) {
                add(it.toString())
            }
        }

        findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = LinearLayoutManager(this)
            it.setOverScrollMode(View.OVER_SCROLL_NEVER)
            mAdapter = SimpleAdapter().apply {
                setDataList(dataList)
                setOnItemClickListener { item, _ ->
                    ToastManager.showToast(item)
                }
                setGridSpace(it, SizeTransferUtil.dip2px(3f, this@LinearAnimActivity))
                setAnimatorType(AnimationType.TRANSLATE_FROM_RIGHT)
            }
            it.adapter = mAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_anim, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val animationType: AnimationType = when (item.itemId) {
            R.id.anim_form_right -> AnimationType.TRANSLATE_FROM_LEFT
            R.id.anim_form_left -> AnimationType.TRANSLATE_FROM_RIGHT
            R.id.anim_form_bottom -> AnimationType.TRANSLATE_FROM_BOTTOM
            R.id.anim_scale -> AnimationType.SCALE
            R.id.anim_alpha -> AnimationType.ALPHA
            else -> throw RuntimeException()
        }
        mAdapter.setAnimatorType(animationType)
        mAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
}