package com.example.utilsuser.coroutine.state_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilsgather.logcat.LogUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SharedFlowActivityViewModelClick : ViewModel() {

    private val _loginFlow  = MutableSharedFlow<String>()

    val loginFlow = _loginFlow.asSharedFlow()

    fun increaseClickCount() {
        viewModelScope.launch {
            _loginFlow.emit("Login Success")
        }
    }
}