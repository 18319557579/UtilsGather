package com.example.utilsgather.application

import android.app.Application
import android.os.Handler
import com.example.utilsgather.kt_room.CommUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommUtils.init(this, Handler(), android.os.Process.myTid())
    }
}