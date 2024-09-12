package com.example.utilsuser.toast

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.ui.toast.ToastHelper
import com.example.utilsgather.ui.toast.ToastManager
import com.example.utilsuser.R
import java.util.Random


class ToastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)

        GuideSettings.set(findViewById<ListView>(R.id.lv_container), arrayOf(
            GuideItemEntity("普通的弹Toast") {
                Toast.makeText(this, "大家好: ${Random().nextInt(10) + 1}", Toast.LENGTH_SHORT)
                    .show()
            },
            GuideItemEntity("取消前一个") {
                ToastHelper.showToast(this, "大家好: ${Random().nextInt(10) + 1}", Toast.LENGTH_SHORT)
            },
            GuideItemEntity("取消前一个，但是有最短展示时间显示") {
                ToastManager.showToast("大家好: ${Random().nextInt(10000) + 1}")
            },
        ))

    }

    override fun onPause() {
        super.onPause()
        ToastManager.cancelAllToast()
    }

}