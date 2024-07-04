package com.example.utilsuser.coroutine.state_flow

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * 当屏幕发生旋转时，数字依旧还在，因为StateFlow是粘性的。
*/
class StateFlowActivityClick : AppCompatActivity() {
    private val stateFlowActivityViewModelClick by viewModels<StateFlowActivityViewModelClick>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        val textView = findViewById<TextView>(R.id.text_view)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stateFlowActivityViewModelClick.clickCountFlow.collect { time ->
                    LogUtil.d("Update time $time in UI.")
                    textView.text = time.toString()
                }
            }
        }

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("点击+1") {
                    stateFlowActivityViewModelClick.increaseClickCount()
                },
            )
        )
    }
}