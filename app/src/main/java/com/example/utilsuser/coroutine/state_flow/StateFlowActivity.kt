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
 * 在该Activity中，发生横竖屏切换时，time会从0重新开始
 */
class StateFlowActivity : AppCompatActivity() {
    private val stateFlowActivityViewModel by viewModels<StateFlowActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)

        val textView = findViewById<TextView>(R.id.text_view)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                //手机横竖屏切换会导致Activity重新创建，重新创建就会使得timeFlow重新被collect，而冷流每次被collect都是要重新执行的。
                stateFlowActivityViewModel.timeFlow.collect { time ->
                    LogUtil.d("Update time $time in UI.")
                    textView.text = time.toString()
                }
            }
        }
    }
}