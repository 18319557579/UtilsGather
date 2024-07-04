package com.example.utilsuser.coroutine.flow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsuser.coroutine.flow_operator.FlowOperatorActivity


class FlowActivity : AppCompatActivity() {
    private val flowActivityViewModel by viewModels<FlowActivityViewModel>()
    private val flowActivityViewModelTimer by viewModels<FlowActivityViewModelTimer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        val textView = findViewById<TextView>(R.id.text_view)

        GuideSettings.set(findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("Start") {
                    lifecycleScope.launch {
                        flowActivityViewModel.timeFlow.collect { time ->
                            textView.text = time.toString()
                            LogUtil.d("Update time $time in UI.")
                        }

                        //当timeFlow的流执行完成后，才可以执行下面的代码。在此案例中由于timeFlow是死循环，因此下面的代码始终无法被执行
                        LogUtil.d("这里能够执行吗")
                    }
                },
                GuideItemEntity("Start（流速不均匀）") {
                    lifecycleScope.launch {
                        //collectLatest剩下未处理完的逻辑不处理了，直接接收新值重新运行处理逻辑，所以有新数据来的时候，delay(3000)将不会受影响
                        flowActivityViewModel.timeFlow.collectLatest  { time ->
                            textView.text = time.toString()
                            delay(3000)
                        }
                    }
                },

                /**
                 * 上面的Start并没有很好地管理Flow的生命周期，它没有与Activity的生命周期同步，而是始终在接收着Flow上游发送过来的数据。
                 * 因此会造成应用切换到后台，还是继续更新ui的情况
                 *
                 * 这里使用launchWhenStarted进行改造，可以达到在后台时，flow不会运行，但是切回前台时，从上次的位置继续。说明保存了数据，浪费空阿
                 */
                GuideItemEntity("使用launchWhenStarted") {
                    lifecycleScope.launchWhenStarted {
                        flowActivityViewModel.timeFlow.collect { time ->
                            textView.text = time.toString()
                            LogUtil.d("Update time $time in UI.")
                        }
                    }
                },

                /**
                 * 使用 repeatOnLifecycle (Lifecycle.State.STARTED)，可以到切换后台后，flow不会运行；切回前台，又重新开始了
                 */
                GuideItemEntity("使用 repeatOnLifecycle (Lifecycle.State.STARTED)") {
                    lifecycleScope.launch {
                        repeatOnLifecycle (Lifecycle.State.STARTED) {
                            flowActivityViewModel.timeFlow.collect { time ->
                                textView.text = time.toString()
                                LogUtil.d("Update time $time in UI.")
                            }
                        }
                    }
                },

                /**
                 * 使用StateFlow来模拟livedata的效果
                 *
                 * 不过和前面两个不同的是，timer在后台继续运行，当切回前台时，会按timer的最新值显示在ui上
                 */
                GuideItemEntity("使用StateFlow来模拟livedata的效果") {
                    flowActivityViewModelTimer.startTimer()

                    lifecycleScope.launch {
                        repeatOnLifecycle (Lifecycle.State.STARTED) {
                            flowActivityViewModelTimer.stateFlow.collect { time ->
                                textView.text = time.toString()
                                LogUtil.d("Update time $time in UI.")
                            }
                        }
                    }
                },
            )
        )

    }
}