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

class LinearAnimActivity : AppCompatActivity() {
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
            it.adapter = SimpleAdapter().apply {
                setDataList(dataList)
                setOnItemClickListener { item, _ ->
                    ToastManager.showToast(item)
                }
                setGridSpace(it, SizeTransferUtil.dip2px(3f, this@LinearAnimActivity))
                setAnimatorType(AnimationType.TRANSLATE_FROM_RIGHT)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_anim, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.anim_form_right -> {

            }
            R.id.anim_form_left -> {

            }
            R.id.anim_scale -> {

            }
            R.id.anim_form_bottom -> {

            }
            R.id.anim_alpha -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}