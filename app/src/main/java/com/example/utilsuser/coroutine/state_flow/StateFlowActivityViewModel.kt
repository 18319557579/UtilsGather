package com.example.utilsuser.coroutine.state_flow

import androidx.lifecycle.ViewModel
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class StateFlowActivityViewModel : ViewModel() {

    val timeFlow = flow {
        var time = 0
        while (true) {
            LogUtil.d("循环继续执行: $time")
            emit(time)
            delay(1000)
            time++
        }
    }
}