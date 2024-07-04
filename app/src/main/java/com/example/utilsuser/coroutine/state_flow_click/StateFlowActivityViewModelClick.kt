package com.example.utilsuser.coroutine.state_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


class StateFlowActivityViewModelClick : ViewModel() {

    private val _clickCountFlow = MutableStateFlow(0)

    val clickCountFlow = _clickCountFlow.asStateFlow()

    fun increaseClickCount() {
        _clickCountFlow.value += 1
    }
}