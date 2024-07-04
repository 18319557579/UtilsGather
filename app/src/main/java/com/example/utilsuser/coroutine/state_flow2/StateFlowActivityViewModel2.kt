package com.example.utilsuser.coroutine.state_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


class StateFlowActivityViewModel2 : ViewModel() {

    private val timeFlow = flow {
        var time = 0
        while (true) {
            LogUtil.d("循环继续执行: $time")
            emit(time)
            delay(1000)
            time++
        }
    }

    val stateFlow =
        timeFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )
}