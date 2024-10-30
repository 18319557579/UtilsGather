package com.example.utilsuser.immersion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.utilsuser.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class CompositeActivity : AppCompatActivity() {
    lateinit var vp2: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composite)

        supportActionBar?.hide()

        tabLayout = findViewById(R.id.tl_head)
        vp2 = findViewById(R.id.vp_body)
        val tabAdapter = TabAdapter(this)
        vp2.adapter = tabAdapter

        val pairs = arrayListOf<Pair<String, ShowFragment>>().apply {
            add("状态栏" to ShowFragment.newInstance(R.layout.activity_email,
                    arrayOf(
                        InnerItemEntity("功能1") { TODO("Not yet implemented") },
                        InnerItemEntity("功能1") { TODO("Not yet implemented") },
                        InnerItemEntity("功能1") { TODO("Not yet implemented") },
                        InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    )
                ))
            add("状态栏" to ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            ))
            add("状态栏" to ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            ))
        }


        for (pair in pairs) {
            tabLayout.addTab(tabLayout.newTab().setText(pair.first))
            tabAdapter.addFragment(pair.second)
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp2.setCurrentItem(tab.position, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.setScrollPosition(position, 0f, true)
            }
        })
    }
}