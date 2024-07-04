package com.example.utilsuser.coroutine.state_flow

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import kotlinx.coroutines.launch

/**
 * 那么只要在5秒钟内横竖屏切换完成了，Flow就不会停止工作。
 * 反过来讲，这也使得程序切到后台之后，如果5秒钟之内再回到前台，那么Flow也不会停止工作。但是如果切到后台超过了5秒钟，Flow就会全部停止了。这点开销还是完全可以接受的。
 */
class StateFlowActivity2 : AppCompatActivity() {
    private val stateFlowActivityViewModel2 by viewModels<StateFlowActivityViewModel2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        val textView = findViewById<TextView>(R.id.text_view)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                //手机横竖屏切换会导致Activity重新创建，重新创建就会使得timeFlow重新被collect，而冷流每次被collect都是要重新执行的。
                stateFlowActivityViewModel2.stateFlow.collect { time ->
                    LogUtil.d("Update time $time in UI.")
                    textView.text = time.toString()
                }
            }
        }
    }
}