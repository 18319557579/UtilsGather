package com.example.uioperate.base_adapter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.uioperate.base_adapter.add_head_foot.AddHeadFootActivity
import com.example.uioperate.base_adapter.anim.GridAnimActivity
import com.example.uioperate.base_adapter.anim.LinearAnimActivity
import com.example.uioperate.base_adapter.anim.StaggeredGridAnimActivity
import com.example.uioperate.base_adapter.grid.GridActivity
import com.example.uioperate.base_adapter.multi_layout.MultiLayoutActivity
import com.example.uioperate.base_adapter.simple.SimpleActivity
import com.example.uioperate.base_adapter.staggered_grid.StaggeredGridActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class BaseAdapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_adapter)

        GuideSettings.set(
            findViewById(R.id.lv_launcher), arrayOf(
                GuideItemEntity("最简单的使用") {
                    startActivity(Intent(this@BaseAdapterActivity, SimpleActivity::class.java))
                },
                GuideItemEntity("多布局的RecyclerView") {
                    startActivity(Intent(this, MultiLayoutActivity::class.java))
                },
                GuideItemEntity("添加头部和底部") {
                    startActivity(Intent(this, AddHeadFootActivity::class.java))
                },
                GuideItemEntity("网格布局") {
                    startActivity(Intent(this, GridActivity::class.java))
                },
                GuideItemEntity("瀑布网格布局") {
                    startActivity(Intent(this, StaggeredGridActivity::class.java))
                },
                GuideItemEntity("Linear 动画") {
                    startActivity(Intent(this, LinearAnimActivity::class.java))
                },
                GuideItemEntity("Grid 动画") {
                    startActivity(Intent(this, GridAnimActivity::class.java))
                },
                GuideItemEntity("StaggeredGrid 动画") {
                    startActivity(Intent(this, StaggeredGridAnimActivity::class.java))
                },
            )
        )
    }
}