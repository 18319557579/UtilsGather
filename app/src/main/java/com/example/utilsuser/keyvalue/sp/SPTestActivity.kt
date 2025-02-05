package com.example.utilsuser.keyvalue.sp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.context.ApplicationGlobal
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R


class SPTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_value)

        val rv = findViewById<ListView>(R.id.lv_launcher_keyvalue)
        GuideSettings.set(rv, arrayOf<GuideItemEntity>(
            GuideItemEntity("写入数据") {
                // 写入数据时，不用传默认值（传也是没有意义的）
                var commonName by SPDelegate<String>(
                    ApplicationGlobal.getInstance(),
                    "name",
                )
                commonName = "hsf"

                var personalAge by SPDelegate<Int> (
                    ApplicationGlobal.getInstance(),
                    "age",
                    spFile = SPUtil.Model.PERSONAL
                )
                personalAge = 26
            },

            GuideItemEntity("读取数据") {
                // 写入数据时，不用传默认值（传也是没有意义的）
                val commonName by SPDelegate(
                    ApplicationGlobal.getInstance(),
                    "name",
                    "kkkkkk"
                )
                LogUtil.d("common 名字: $commonName")

                // 由于没有保存name2，因此这里实际上使用了默认值
                val commonName2 by SPDelegate(
                    ApplicationGlobal.getInstance(),
                    "name2",
                    "kkkkkk"
                )
                LogUtil.d("common 名字2: $commonName2")

                val personalAge by SPDelegate(
                    ApplicationGlobal.getInstance(),
                    "age",
                    132,
                    spFile = SPUtil.Model.PERSONAL
                )
                LogUtil.d("personal 年龄: $personalAge")
            },
        ))
    }
}