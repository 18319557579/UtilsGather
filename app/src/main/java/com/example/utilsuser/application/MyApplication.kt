package com.example.utilsuser.application

import android.app.Application
import android.os.Handler
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.BuildConfig
import com.example.utilsuser.kt_room.CommUtils
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommUtils.init(this, Handler(), android.os.Process.myTid())

        XLog.init(LogLevel.ALL);

        val rootDir = MMKV.initialize(this)
        LogUtil.d("mmkv root: $rootDir")

        EventBus.builder()
            // have a look at the index class to see which methods are picked up
            // if not in the index @Subscribe methods will be looked up at runtime (expensive)
            .addIndex(com.example.uioperate.fragment.communication_evnetbus.MyEventBusIndex())
            // 在出现异常的时候，是否抛出异常，这里根据是否为DEBUG模式来确定是否抛出异常
            // 因为在默认情况下，EventBus会捕获这些异常
            .throwSubscriberException(BuildConfig.DEBUG)
            .installDefaultEventBus()
    }
}