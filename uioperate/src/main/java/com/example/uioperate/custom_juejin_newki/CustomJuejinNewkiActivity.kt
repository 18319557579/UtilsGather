package com.example.uioperate.custom_juejin_newki

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class CustomJuejinNewkiActivity : AppCompatActivity() {
    lateinit var btnSetProgress2: Button
    lateinit var myCircleProgressView: MyCircleProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_juejin_newki)

        btnSetProgress2 = findViewById(R.id.btn_set_progress_2)
        myCircleProgressView = findViewById(R.id.mcpv)

        btnSetProgress2.setOnClickListener {
            val vvv : MyCircleProgressView= findViewById(R.id.mcpv2)
            vvv.setupValue(0f, 1f)
        }
        GuideSettings.setHorizontal(
            findViewById(R.id.rv_horizontal),
            arrayOf(
                GuideItemEntity("当前进度0,目标进度0.8") {
                    myCircleProgressView.setupValue(0f, 0.8f)
                },
                GuideItemEntity("当前进度0,目标进度1") {
                    myCircleProgressView.setupValue(0f, 1f)
                },
                GuideItemEntity("当前进度0.2,目标进度0.98") {
                    myCircleProgressView.setupValue(0.2f, 0.98f)
                },
                GuideItemEntity("当前进度0.2,目标进度0.99") {
                    myCircleProgressView.setupValue(0.2f, 0.99f)
                },
            )
        )
    }
}