package com.example.uioperate.base_adapter.multi_layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.utilsgather.ui.toast.ToastManager

class MultiLayoutActivity : AppCompatActivity() {
    private val mDataList: ArrayList<MainBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_layout)
        addData()

        findViewById<RecyclerView>(R.id.recyclerView).adapter = MainAdapter().apply {
            setDataList(mDataList)
            setOnItemClickListener { item, _ ->
                ToastManager.showToast(item.toString())
            }
        }
    }

    private fun addData() {
        mDataList.add(MainBean(getString(R.string.simple_use), getString(R.string.simple_des)))
        mDataList.add(MainBean(getString(R.string.muti_use), getString(R.string.muti_des)))
        mDataList.add(MainBean(getString(R.string.addhead_use), getString(R.string.addhead_des)))
        mDataList.add(MainBean(getString(R.string.grid_use), getString(R.string.grid_des)))
        mDataList.add(MainBean(getString(R.string.pull_use), getString(R.string.pull_des)))
        mDataList.add(MainBean(getString(R.string.animation_use), getString(R.string.animation_des)))
        mDataList.add(
            MainBean(
                getString(R.string.shadowclick_use),
                getString(R.string.shadowclick_des)
            )
        )
        mDataList.add(MainBean(getString(R.string.notify_use), getString(R.string.notify_des)))
        mDataList.add(MainBean(getString(R.string.swip_use), getString(R.string.swip_des)))
        mDataList.add(
            MainBean(
                getString(R.string.swip_slide_use),
                getString(R.string.swip_slide_des)
            )
        )
        mDataList.add(MainBean(getString(R.string.smart_use), getString(R.string.smart_des)))
        mDataList.add(MainBean(getString(R.string.expand_use), getString(R.string.expand_des)))
        mDataList.add(MainBean(getString(R.string.pager_use), getString(R.string.pager_des)))
        mDataList.add(MainBean(getString(R.string.span_use), getString(R.string.span_des)))
        mDataList.add(MainBean(getString(R.string.recover_use), getString(R.string.recover_des)))
        mDataList.add(MainBean(getString(R.string.fold_use), getString(R.string.fold_des)))
        mDataList.add(MainBean(getString(R.string.sticky_use), getString(R.string.sticky_des)))
        mDataList.add(MainBean(getString(R.string.coord_use), getString(R.string.coord_des)))
    }
}