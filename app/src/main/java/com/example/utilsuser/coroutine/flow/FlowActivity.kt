package com.example.utilsuser.coroutine.flow

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {
    private val flowActivityViewModel by viewModels<FlowActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        val textView = findViewById<TextView>(R.id.text_view)
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            lifecycleScope.launch {
                flowActivityViewModel.timeFlow.collect { time ->
                    textView.text = time.toString()
                }
                //当timeFlow的流执行完成后，才可以执行下面的代码。在此案例中由于timeFlow是死循环，因此下面的代码始终无法被执行
                LogUtil.d("这里能够执行吗")
            }
        }

        button2.setOnClickListener {
            lifecycleScope.launch {
                //collectLatest剩下未处理完的逻辑不处理了，直接接收新值重新运行处理逻辑，所以有新数据来的时候，delay(3000)将不会受影响
                flowActivityViewModel.timeFlow.collectLatest  { time ->
                    textView.text = time.toString()
                    delay(3000)
                }
            }
        }

    }
}