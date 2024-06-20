package com.example.utilsuser.keyvalue

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import com.tencent.mmkv.MMKV



class KeyValueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_value)

        val rv = findViewById<ListView>(R.id.lv_launcher_keyvalue)
        GuideSettings.set(rv, arrayOf<GuideItemEntity>(
            GuideItemEntity("写入数据") {
                val kv = MMKV.defaultMMKV()

                kv.encode("isMan", true)

                kv.encode("age", 15)

                kv.encode("desc", "Hello from mmkv")
            },

            GuideItemEntity("打印存入的信息") {
                val kv = MMKV.defaultMMKV()

                val bValue: Boolean = kv.decodeBool("isMan")
                LogUtil.d("isMan: $bValue")

                val iValue: Int = kv.decodeInt("age")
                LogUtil.d("age: $iValue")

                val str: String = kv.decodeString("desc").toString()
                LogUtil.d("desc: $str")
            },

            GuideItemEntity("删除key") {
                val kv = MMKV.defaultMMKV()
                kv.removeValueForKey("isMan")

                kv.remove("age")

                kv.removeValuesForKeys(arrayOf("desc"))
                LogUtil.d("已删除")
                /*kv.encode("isMan", true)
                val bValue: Boolean = kv.decodeBool("isMan")
                LogUtil.d("isMan: $bValue")

                kv.encode("age", 15)
                val iValue: Int = kv.decodeInt("age")
                LogUtil.d("age: $iValue")

                kv.encode("desc", "Hello from mmkv")
                val str: String = kv.decodeString("desc").toString()
                LogUtil.d("age: $str")*/
            },
        ))
    }
}