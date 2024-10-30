package com.example.utilsuser.immersion

import android.util.Pair
import com.example.utilsuser.R

class CompositeActivity : BaseTabViewpagerActivity() {
    override fun addPairs(pairs: MutableList<Pair<String, ShowFragment>>) {
        pairs.apply {
            add(Pair("状态栏", ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            )))
            add(Pair("状态栏", ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            )))
            add(Pair("状态栏", ShowFragment.newInstance(
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            )))
            add(Pair("状态栏", ShowFragment.newInstance(R.layout.activity_email,
                arrayOf(
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                    InnerItemEntity("功能1") { TODO("Not yet implemented") },
                )
            )))
        }
    }
}