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
import kotlinx.coroutines.launch

/**
 * SharedFlow不是粘性的，当屏幕发生旋转后里面的值不会有了
*/
class SharedFlowActivityClick : AppCompatActivity() {
    private val sharedFlowActivityViewModelClick by viewModels<SharedFlowActivityViewModelClick>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        val textView = findViewById<TextView>(R.id.text_view)
        textView.text = "状态未知"

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedFlowActivityViewModelClick.loginFlow.collect { time ->
                    textView.text = time
                }
            }
        }

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("将状态改为Success") {
                    sharedFlowActivityViewModelClick.increaseClickCount()
                },
            )
        )
    }
}