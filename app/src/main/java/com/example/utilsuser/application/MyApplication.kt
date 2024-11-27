package com.example.utilsuser.application

import android.app.Application
import android.os.Handler
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.kt_room.CommUtils
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommUtils.init(this, Handler(), android.os.Process.myTid())

        val rootDir = MMKV.initialize(this)
        LogUtil.d("mmkv root: $rootDir")

        EventBus.builder()
            // have a look at the index class to see which methods are picked up
            // if not in the index @Subscribe methods will be looked up at runtime (expensive)
            .addIndex(com.example.uioperate.fragment.communication_evnetbus.MyEventBusIndex())
            .installDefaultEventBus()
    }
}