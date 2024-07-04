package com.example.utilsuser.coroutine.flow

import androidx.lifecycle.ViewModel
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.util.Timer
import java.util.TimerTask

class FlowActivityViewModelTimer : ViewModel() {
    private val _stateFlow = MutableStateFlow(0)

    val stateFlow = _stateFlow.asStateFlow()

    fun startTimer() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                LogUtil.d("循环继续执行: ${_stateFlow.value}")
                _stateFlow.value += 1
            }
        }, 0, 1000)
    }
}