package com.example.uioperate.fragment.communication_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Zoo3ActivityViewModel : ViewModel(){
    private val _MonkeyObserve = MutableLiveData<Int>()
    val monkeyObserve: LiveData<Int> get() = _MonkeyObserve
    fun setMonkeyObserve(message: Int) {
        _MonkeyObserve.value = message
    }

    private val _fishObserve = MutableLiveData<Long>()
    val fishObserve: LiveData<Long> get() = _fishObserve
    fun setFishObserve(message: Long) {
        _fishObserve.value = message
    }

    private val _activityObserve = MutableLiveData<String>()
    val activityObserve: LiveData<String> get() = _activityObserve
    fun setActivityObserve(message: String) {
        _activityObserve.value = message
    }
}