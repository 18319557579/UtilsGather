package com.example.uioperate.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil

class DynamicLoadActivity : LifecycleLogActivity() {
    lateinit var rlMain: FrameLayout

    val oneFragment by lazy {
        BananaFragment("一万")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_load)

        addFragment(oneFragment)  //加入到队列里，延迟执行
//        addFragment(oneFragment, true)  //生命周期与静态添加一致

        rlMain = findViewById(R.id.rl_main)
        GuideSettings.setHorizontal(
            findViewById(R.id.rv_horizontal),
            arrayOf(
                GuideItemEntity("打印栈信息") {
                    printFragmentStack()
                },

                //重复添加会报错闪退
                GuideItemEntity("add") {
                    addFragment(oneFragment)
                },

                //本质上调用View.setVisibility(GONE)，不会回调Fragment 生命周期中的方法。
                GuideItemEntity("hide") {
                    hideFragment(oneFragment)
                },

                //本质上调用View.setVisibility(VISIBLE) 不会回调Fragment 生命周期中的方法。
                GuideItemEntity("show") {
                    showFragment(oneFragment)
                },

                //将Fragment 从Activity 中移除，实际上是将Fragment 关联的View 从ViewTree中移除，但还在回退栈中
                GuideItemEntity("detach") {
                    detachFragment(oneFragment)
                },

                GuideItemEntity("remove") {
                    removeFragment(oneFragment)
                }
            )
        )
    }

    fun addFragment(fragment: Fragment, isCommitNow: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.rl_main, fragment)
//        transaction.addToBackStack("hsf")
        if (isCommitNow) {
            transaction.commitNow()
        } else {
            transaction.commit()
        }
    }

    fun hideFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.hide(fragment)
        transaction.commit()
    }

    fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.show(fragment)
        transaction.commit()
    }

    fun detachFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.detach(fragment)
        transaction.commit()
    }

    fun removeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }

    /**
     * 打印当前的fragment栈信息
     */
    fun printFragmentStack() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragments: List<Fragment> = fragmentManager.getFragments()
        LogUtil.d("打印fragment栈信息：")
        for (fragment in fragments) {
            if (fragment != null) {
                LogUtil.d(("Fragment: " + fragment.javaClass.getSimpleName()).toString()
                        + " Tag: " + fragment.getTag())
            }
        }
    }
}