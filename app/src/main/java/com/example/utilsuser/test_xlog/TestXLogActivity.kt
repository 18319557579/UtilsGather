package com.example.utilsuser.test_xlog

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import com.elvishew.xlog.LogUtils
import com.elvishew.xlog.XLog

import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsuser.R
import com.example.utilsuser.application.MyApplication
import com.example.utilsuser.xlog.XLogConstant
import com.example.utilsuser.xlog.XLogGlobal
import java.lang.Exception

/**
 * 配合style使用的Activity，但效果还是不佳
 */
class TestXLogActivity : LifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_common)

        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.lv_launcher)
        GuideSettings.set(
            listView, arrayOf<GuideItemEntity>(
                GuideItemEntity("基础打印"){
                    XLog.d("你好 xlog");

                    try {
                        val a = 4 / 0
                    } catch (e: Exception) {
                        // 可以用于那些会抛出异常的地方，因为它既不会让系统闪退，又能打印栈信息
                        XLog.e("出错了", e);
                    }

                    // 可以让Java的日志打印稍微方便一点，内部自动进行了格式化。但是Kotlin不太需要了
                    XLog.d("你好%s，我今年 %d 岁", "Elvis", 20);

                    val json = "{\"name\":\"hsf\",\"age\":25,\"exam\":[{\"subject\":\"math\",\"scope\":16.5},{\"subject\":\"english\",\"scope\":79}]}"
                    XLog.d(json);
                    // 对比普通的输出，输出的JSON数据带缩进
                    XLog.json(json)

                    val xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\" xmlns:app=\"http://schemas.android.com/apk/res-auto\" xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                            "    android:id=\"@+id/main\"\n" +
                            "    android:layout_width=\"match_parent\"\n" +
                            "    android:layout_height=\"match_parent\"\n" +
                            "    tools:context=\".qihang.four.AnimateLayoutChangesActivity\"\n" +
                            "    android:orientation=\"vertical\"><LinearLayout\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"wrap_content\"\n" +
                            "        android:orientation=\"horizontal\"><Button\n" +
                            "            android:id=\"@+id/add_btn\"\n" +
                            "            android:layout_width=\"wrap_content\"\n" +
                            "            android:layout_height=\"wrap_content\"\n" +
                            "            android:text=\"添加控件\"/><Button\n" +
                            "            android:id=\"@+id/remove_btn\"\n" +
                            "            android:layout_width=\"wrap_content\"\n" +
                            "            android:layout_height=\"wrap_content\"\n" +
                            "            android:text=\"移除控件\"/></LinearLayout><LinearLayout\n" +
                            "        android:id=\"@+id/linear_layout_container\"\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"wrap_content\"\n" +
                            "        android:animateLayoutChanges=\"true\"\n" +
                            "        android:orientation=\"vertical\"/></LinearLayout>"
                    XLog.d(xml);
                    // 对比普通的输出，输出的XML数据更美观
                    XLog.xml(xml)

                    val array = arrayOf("h3i", "3h", "32h2h", "cnha")
                    XLog.d(array)

                    val list = listOf("fi3", 23.38, 87, false)
                    XLog.d(list)

                    val map = mapOf("name" to "hsf", "age" to 13, "height" to 34.9)
                    XLog.d(map)

                    val intent = Intent(this, TestXLogActivity::class.java).apply {
                        putExtra("name", "hsf")
                        putExtra("index", 'B')
                    }
                    val bundle = Bundle().apply {
                        putString("location", "dsf")
                        putInt("num", 100)
                        putBoolean("isMale", false)
                    }
                    intent.putExtras(bundle)
                    XLog.d(intent);
                    XLog.d(bundle);

                    // 打印任意对象，如果没有指定ObjectFormatter，则在对象转换为字符串时，直接调用对象的toString()方法
                    XLog.d(this)
                },
                GuideItemEntity("更高级用法") {
                    // 上面XLog打印的话，内部有一个全局的Logger

                    MyApplication.copyTagLogger.d("你好 xlog")
                    MyApplication.copyTagLogger1.d("你好 xlog")

                    // 其实内部创建了一个独立Logger来打印日志。很显然，这个Logger除了tag以外的信息，都是取自全局Logger
                    // 但是，这样每次都要创建一个Logger，对资源是一种消耗
                    XLog.tag("TAG-A").d("定制了 TAG 的消息");
                },
                GuideItemEntity("使用XLogGlobal 的 独立Logger") {
                    XLogGlobal.logger(
                        XLogConstant.COPY_FUNCTION).d("复制成功！")
                },
                GuideItemEntity("压缩日志文件") {
                    LogUtils.compress(
                        XLogConstant.getFolderPath(),
                        XLogConstant.getZipPath())
                },
            )
        )
    }
}