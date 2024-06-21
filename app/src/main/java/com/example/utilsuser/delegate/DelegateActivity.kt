package com.example.utilsuser.delegate

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R

class DelegateActivity : AppCompatActivity() {

    val p by later {
        LogUtil.d("已经初始化")
        "test later"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delegate)
    }

    fun getDelegate(view: View) {
        LogUtil.d("获取到的p的值: ${p}")
    }
}