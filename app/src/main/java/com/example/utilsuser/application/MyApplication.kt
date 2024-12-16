package com.example.utilsuser.application

import android.app.Application
import android.os.Handler
import com.elvishew.xlog.Logger
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.BuildConfig
import com.example.utilsuser.kt_room.CommUtils
import com.example.utilsuser.xlog.XLogGlobal
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus

class MyApplication : Application() {
    companion object {
        lateinit var copyTagLogger: Logger
        lateinit var copyTagLogger1: Logger
    }

    override fun onCreate() {
        super.onCreate()
        CommUtils.init(this, Handler(), android.os.Process.myTid())

        /*// 其实全局打印、Logger打印、一次性打印对应着：全局那个Logger、独立创建的Logger、一次性创建用完找不回的Logger
        // 不过注意，全局那个Logger是必须创建的，因为它能使得sIsInitialized字段为true，即初始化完成

        // 如果说想要自定义多个独立Logger，但是觉得全局Logger的设置会导致碍事，可以用最简单地XLog.init()来创建一个Logger，但是不使用它，仅仅是为了初始化XLog，后续创建独立Logger来使用即可
        // 然后有小情况不能用独立Logger概括的，例如它就是要边框而独立Logger没有，就创建一次性Logger来实现

        val config = LogConfiguration.Builder()
            .logLevel(LogLevel.VERBOSE)
            .tag("Daisy_TAG")
            .enableThreadInfo()
            .enableStackTrace(3)
            .enableBorder()
            .addInterceptor(BlacklistTagsFilterInterceptor("BlackTest"))
            .build()
        XLog.init(config);

        *//* 这些非全局的Logger，在调用build()方法时，会去拿全局的LogConfiguration，最终都是调用LogConfiguration来设置LogConfiguration自己的字段，并把LogConfiguration对象作为Logger的成员变量。
        但是，在设置字段之前，会经过Logger.Builder字段的过滤，也就是上面.enableThreadInfo()这个调用的作用：去设置Logger.Builder字段
        （注：XLog.d() 或者 Logger.d()，那些样式的打印与否判断，其实就是基于Logger的成员变量logConfiguration 中的字段
         *//*
        copyTagLogger = XLog.tag("Copy_TAG")
            .disableBorder()
            .disableThreadInfo()
            .enableStackTrace(1)
            .addInterceptor(BlacklistTagsFilterInterceptor())  // 看源码，interceptors会覆盖全局Logger中的interceptors中的interceptors
            .build()

        // 由于前面配置了tag黑名单，并且这里会沿用全局的配置，所以不会打印这个日志
        copyTagLogger1 = XLog.tag("BlackTest")
            .build()*/
        XLogGlobal.init()

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