package com.example.utilsgather

import android.content.Intent
import android.os.Bundle
import com.example.uioperate.UiOperateEntranceActivity
import com.example.utilsgather.application_store.AppStoreUtil
import com.example.utilsgather.assets.AssetsUtil
import com.example.utilsgather.browser.BrowserUtil
import com.example.utilsgather.databinding.ActivityMainBinding
import com.example.utilsgather.handler.HandlerUI
import com.example.utilsgather.lifecycle_callback.CallbackActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.map.MapUtil
import com.example.utilsgather.package_info.PackageInfoUtil
import com.example.utilsgather.source_file.SourceUtil
import com.example.utilsgather.thread.ThreadUtil
import com.example.utilsgather.ui.ScreenFunctionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : CallbackActivity() {
    var mainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding!!.getRoot())
        GuideSettings.set(
            mainBinding!!.lvLauncher, arrayOf<GuideItemEntity>(
                GuideItemEntity("去UIOperate") {
                    val intent = Intent(this@MainActivity, UiOperateEntranceActivity::class.java)
                    startActivity(intent)
                },
                GuideItemEntity("测试获取应用名") {
                    PackageInfoUtil.getAppNameByUrl("xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage")
                },
                GuideItemEntity("测试从子线程切换为UI线程") {
                    LogUtil.d("当前的线程是：" + Thread.currentThread())
                    HandlerUI.runOnUI { LogUtil.d("当前的线程是2：" + Thread.currentThread()) }
                },
                GuideItemEntity("从Assets中读出内容") {
                    LogUtil.d(
                        """
    读出来的文本:
    ${AssetsUtil.readAssetsFile(this@MainActivity, "uiautomatorviewer.txt")}
    """.trimIndent()
                    )
                },
                GuideItemEntity("在谷歌中打开小红书") {
                    AppStoreUtil.jumpStore(
                        this@MainActivity, "com.xingin.xhs", true
                    )
                },
                GuideItemEntity("在浏览器中打开京东") {
                    BrowserUtil.jumpBrowser(
                        "https://m.jd.com/", this@MainActivity
                    )
                },
                GuideItemEntity("能不能让我选一下跳转去哪") {
                    BrowserUtil.jumpOthers(
                        this@MainActivity, "https://www.baidu.com"
                    )
                },
                GuideItemEntity("通过value找到key") {
                    val myMap: MutableMap<Float, String> = HashMap()
                    myMap[0.1f] = "hsf"
                    myMap[0.2f] = "hwt"
                    myMap[0.3f] = "hqs"
                    LogUtil.d("找到的KEY：" + MapUtil.getKey(myMap, "hsf"))
                },
                GuideItemEntity("设置屏幕常亮") { ScreenFunctionUtils.setScreenOn(this@MainActivity) },
                GuideItemEntity("从raw中读出图片") {
                    /*Thread {
                        val bitmap = SourceUtil.getBitmapFromRaw(this@MainActivity, R.raw.set_meal)
                        runOnUiThread { mainBinding!!.ivContent.setImageBitmap(bitmap) }
                    }.start()*/

                    GlobalScope.launch(Dispatchers.Main) {
                        LogUtil.d("打印当前线程：${ThreadUtil.getCurrentThread()}")
                        val bitmap =  withContext(Dispatchers.IO) {
                            LogUtil.d("打印当前线程：${ThreadUtil.getCurrentThread()}")
                            SourceUtil.getBitmapFromRaw(this@MainActivity, R.raw.set_meal)
                        }
                        mainBinding!!.ivContent.setImageBitmap(bitmap)
                    }
                })
        )
    }
}