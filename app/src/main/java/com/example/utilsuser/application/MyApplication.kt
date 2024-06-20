package com.example.utilsuser.application

import android.app.Application
import android.os.Handler
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.kt_room.CommUtils
import com.tencent.mmkv.MMKV

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommUtils.init(this, Handler(), android.os.Process.myTid())

        val rootDir = MMKV.initialize(this)
        LogUtil.d("mmkv root: $rootDir")
    }
}