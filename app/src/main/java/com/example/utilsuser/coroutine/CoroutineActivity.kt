package com.example.utilsuser.coroutine

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import com.example.utilsuser.StyleImmersionActivity
import com.example.utilsuser.coroutine.flow.FlowActivity
import com.example.utilsuser.coroutine.flow_operator.FlowOperatorActivity
import io.reactivex.schedulers.Schedulers.start
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class CoroutineActivity : AppCompatActivity() {
    val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        GuideSettings.set(findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("去 FlowActivity") {
                    startActivity(Intent(this, FlowActivity::class.java))
                },
                GuideItemEntity("去 FlowOperatorActivity") {
                    startActivity(Intent(this, FlowOperatorActivity::class.java))
                },
            )
        )

        GlobalScope.launch {
            LogUtil.d("名称: ${Thread.currentThread().name}")
        }

        thread {
            LogUtil.d("名称: ${Thread.currentThread().name}")
        }

        /*//现在下面的情况是：顺序反了，先执行ui，再执行io
        GlobalScope.launch {
            ioCode1()
        }
        uiCode1()*/

        //
        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()

            ioCode2()
            uiCode2()

            ioCode3()
            uiCode3()
        }

        val job = GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                val repo1 = "rengwuxian"
                val repo2 = "google"
                "$repo1 and $repo2"
            }
            LogUtil.d("得到了最后的结果: $result")
        }
        job.cancel()

        scope.launch {
            val result = withContext(Dispatchers.IO) {
                val repo1 = "rengwuxian"
                val repo2 = "google"
                "$repo1 and $repo2"
            }
            LogUtil.d("得到了最后的结果: $result")
        }

        lifecycleScope.launch {

        }

        lifecycleScope.launchWhenCreated {

        }
    }

    suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            LogUtil.d("io 名称1: ${Thread.currentThread().name}")
        }
    }
    fun uiCode1() {
        LogUtil.d("ui 名称1: ${Thread.currentThread().name}")
    }
    suspend fun ioCode2() {
        withContext(Dispatchers.IO) {
            LogUtil.d("io 名称2: ${Thread.currentThread().name}")
        }
    }
    fun uiCode2() {
        LogUtil.d("ui 名称2: ${Thread.currentThread().name}")
    }
    suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            LogUtil.d("io 名称3: ${Thread.currentThread().name}")
        }
    }
    fun uiCode3() {
        LogUtil.d("ui 名称3: ${Thread.currentThread().name}")
    }
}