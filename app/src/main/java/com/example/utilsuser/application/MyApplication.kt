package com.example.utilsuser.application

import android.app.Application
import android.os.Handler
import com.example.utilsuser.kt_room.CommUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommUtils.init(this, Handler(), android.os.Process.myTid())
    }
}