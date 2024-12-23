package com.example.utilsuser.coroutine.juejin_sheying

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.ui.toast.ToastHelper
import com.example.utilsuser.R
import com.example.utilsuser.coroutine.flow.FlowActivity
import com.example.utilsuser.coroutine.flow_operator.FlowOperatorActivity
import com.example.utilsuser.coroutine.state_flow.SharedFlowActivityClick
import com.example.utilsuser.coroutine.state_flow.StateFlowActivity
import com.example.utilsuser.coroutine.state_flow.StateFlowActivity2
import com.example.utilsuser.coroutine.state_flow.StateFlowActivityClick
import com.example.utilsuser.xlog.XLogConstant
import com.example.utilsuser.xlog.XLogGlobal
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class CoroutineStudyActivity : AppCompatActivity() {

    init {
        lifecycleScope.launchWhenResumed {
            XLogGlobal.logger(XLogConstant.Coroutine).d("在类初始化位置启动协程")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        GuideSettings.set(findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("启动一个协程") {
                    start()
                },
                GuideItemEntity("启动一个协程，增加变量") {
                    start2()
                },
                GuideItemEntity("在 GlobalScope.launch 启动") {
                    start3()
                },
                GuideItemEntity("循环10次输出日志") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    start4()
                },
                GuideItemEntity("循环10次输出日志， 在Dispatchers.Main") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    start5()
                },
                GuideItemEntity("CoroutineContext测试") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineContext()
                },
                GuideItemEntity("CoroutineStart测试") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineStart()
                },
                GuideItemEntity("测试UnDispatched效果") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testUnDispatched()
                },
                GuideItemEntity("测试UnDispatched效果，真实使用UnDispatched") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testUnDispatchedReal()
                },
                GuideItemEntity("测试UnDispatched效果，真实使用UnDispatched，不设置context") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testUnDispatchedRealNoDispatchersUsed()
                },
                GuideItemEntity("子协程会继承父协程的协程上下文中的Element，以及相同Key的覆盖") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineScope()
                },
                GuideItemEntity("测试协同作用域") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineScope2()
                },
                GuideItemEntity("测试主从作用域") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineScope3()
                },
                GuideItemEntity("测试主从作用域，使用SupervisorJob") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineScope4()
                },
                GuideItemEntity("测试 协程异常的产生") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testCoroutineExceptionHandler()
                },
                GuideItemEntity("测试 协程异常的产生，只在父协程上添加CoroutineExceptionHandler") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testException()
                },
                GuideItemEntity("测试 协程异常的产生，使用supervisorScope ") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testExceptionSupervisorScope()
                },
                GuideItemEntity("测试 协程异常的产生，使用testExceptionSupervisorJob ") {
                    XLogGlobal.logger(XLogConstant.Coroutine).dLine()
                    testExceptionSupervisorJob()
                },
            )
        )

        doOnCreate()
    }

    private fun start(){
        runBlocking {
            XLogGlobal.logger(XLogConstant.Coroutine).d("runBlocking 启动一个协程")
        }
        GlobalScope.launch{
            XLogGlobal.logger(XLogConstant.Coroutine).d("launch 启动一个协程")
        }
        GlobalScope.async{
            XLogGlobal.logger(XLogConstant.Coroutine).d("async 启动一个协程")
        }
    }

    private fun start2(){
        XLogGlobal.logger(XLogConstant.Coroutine).dLine()
        val runBlockingJob = runBlocking {
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("runBlocking 启动一个协程")
            41
        }
        XLogGlobal.logger(XLogConstant.Coroutine).dThread("runBlockingJob $runBlockingJob")
        val launchJob = GlobalScope.launch{
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("launch 启动一个协程")
        }
        XLogGlobal.logger(XLogConstant.Coroutine).dThread("launchJob $launchJob")
        val asyncJob = GlobalScope.async{
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("async 启动一个协程")
            "我是返回值"
        }
        XLogGlobal.logger(XLogConstant.Coroutine).dThread("asyncJob $asyncJob")
    }

    private fun start3(){
        XLogGlobal.logger(XLogConstant.Coroutine).dLine()
        GlobalScope.launch{
            val launchJob = launch{
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("launch 启动一个协程")
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("launchJob $launchJob")
            val asyncJob = async{
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("async 启动一个协程")
                "我是async返回值"
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("asyncJob.await :${asyncJob.await()}")
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("asyncJob $asyncJob")
        }
    }

    private fun start4() {
        GlobalScope.launch(Dispatchers.Default) {
            for (index in 1 until  10) {
                //并发执行
                launch {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("launch$index 启动一个协程")
                }
            }
        }
    }

    private fun start5() {
        GlobalScope.launch(Dispatchers.Main) {
            for (index in 1 until  10) {
                //并发执行
                launch {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("launch$index 启动一个协程")
                }
            }
        }
    }

    private fun testCoroutineContext(){
        val coroutineContext1 = Job() + CoroutineName("这是第一个上下文")
        XLogGlobal.logger(XLogConstant.Coroutine).dThread("coroutineContext1 $coroutineContext1")
        val  coroutineContext2 = coroutineContext1 + Dispatchers.Default + CoroutineName("这是第二个上下文")
        XLogGlobal.logger(XLogConstant.Coroutine).dThread(("coroutineContext2 $coroutineContext2"))
        val coroutineContext3 = coroutineContext2 + Dispatchers.Main + CoroutineName("这是第三个上下文")
        XLogGlobal.logger(XLogConstant.Coroutine).dThread(("coroutineContext3 $coroutineContext3"))
    }

    private fun testCoroutineStart(){
        val defaultJob = GlobalScope.launch{
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("defaultJob CoroutineStart.DEFAULT")
        }
        defaultJob.cancel()
        val lazyJob = GlobalScope.launch(start = CoroutineStart.LAZY){
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("lazyJob CoroutineStart.LAZY")
        }
        val atomicJob = GlobalScope.launch(start = CoroutineStart.ATOMIC){
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("atomicJob CoroutineStart.ATOMIC挂起前")
            delay(100)
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("atomicJob CoroutineStart.ATOMIC挂起后")
        }
        atomicJob.cancel()
        val undispatchedJob = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED){
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("undispatchedJob CoroutineStart.UNDISPATCHED挂起前")
            delay(100)
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("undispatchedJob CoroutineStart.UNDISPATCHED挂起后")
        }
        undispatchedJob.cancel()
    }

    private fun testUnDispatched(){
        GlobalScope.launch(Dispatchers.Main){
            val job = launch(Dispatchers.IO) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> 挂起前")
                delay(100)
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> 挂起后")
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> join前")
            job.join()
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> join后")
        }
    }

    private fun testUnDispatchedReal(){
        GlobalScope.launch(Dispatchers.Main){
            val job = launch(Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> 挂起前")
                delay(100)
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> 挂起后")
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> join前")
            job.join()
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> join后")
        }
    }

    private fun testUnDispatchedRealNoDispatchersUsed(){
        GlobalScope.launch(Dispatchers.Main){
            val job = launch(start = CoroutineStart.UNDISPATCHED) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> 挂起前")
                delay(100)
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> 挂起后")
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> join前")
            job.join()
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}线程 -> join后")
        }
    }

    private fun  testCoroutineScope(){
        GlobalScope.launch(Dispatchers.Main){
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("父协程上下文 $coroutineContext")
            launch(CoroutineName("第一个子协程")) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("第一个子协程上下文 $coroutineContext")
            }
            launch(Dispatchers.Unconfined) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("第二个子协程协程上下文 $coroutineContext")
            }
            launch(Dispatchers.IO) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("第二个子协程协程上下文 $coroutineContext")
            }
        }
    }

    private fun testCoroutineScope2() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("exceptionHandler ${coroutineContext[CoroutineName]} $throwable")
        }
        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 1")
            launch(CoroutineName("scope2") + exceptionHandler) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 2")
                throw  NullPointerException("空指针")
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 3")
            }
            val scope3 = launch(CoroutineName("scope3") + exceptionHandler) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 4")
                delay(2000)
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 5")
            }
            scope3.join()
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 6")
        }
    }

    private fun testCoroutineScope3() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("exceptionHandler ${coroutineContext[CoroutineName]} $throwable")
        }
        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            supervisorScope {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope  --------- 1")
                launch(CoroutineName("scope2")) {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 2")
                    throw  NullPointerException("空指针")
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 3")
                    val scope3 = launch(CoroutineName("scope3")) {
                        XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 4")
                        delay(2000)
                        XLogGlobal.logger(XLogConstant.Coroutine).dThread("scop --------- 5")
                    }
                    scope3.join()
                }
                val scope4 = launch(CoroutineName("scope4")) {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 6")
                    delay(2000)
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 7")
                }
                scope4.join()
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope --------- 8")
            }
        }
    }

    private fun testCoroutineScope4() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("exceptionHandler ${coroutineContext[CoroutineName]} $throwable")
        }
        val coroutineScope = CoroutineScope(SupervisorJob() + CoroutineName("coroutineScope AAA"))
        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            with(coroutineScope){
                val scope2 = launch(CoroutineName("scope2") + exceptionHandler) {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope 1--------- ${coroutineContext[CoroutineName]}")
                    throw  NullPointerException("空指针")
                }
                val scope3 = launch(CoroutineName("scope3") + exceptionHandler) {
                    scope2.join()
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope 2--------- ${coroutineContext[CoroutineName]}")
                    delay(2000)
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope 3--------- ${coroutineContext[CoroutineName]}")
                }
                scope2.join()
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope 4--------- ${coroutineContext[CoroutineName]}")
                coroutineScope.cancel()
                scope3.join()
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope 5--------- ${coroutineContext[CoroutineName]}")
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("scope 6--------- ${coroutineContext[CoroutineName]}")
        }
    }

    private fun testCoroutineExceptionHandler(){
        GlobalScope.launch {
            val job = launch {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} 抛出未捕获异常")
                throw NullPointerException("异常测试")
            }
            job.join()
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} end")
        }
    }

    private fun testException(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("exceptionHandler ${coroutineContext[CoroutineName]} 处理异常 ：$throwable")
        }
        GlobalScope.launch(CoroutineName("父协程") + exceptionHandler){
            val job = launch(CoroutineName("子协程")) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} 我要开始抛异常了" )
                for (index in 0..10){
                    launch(CoroutineName("孙子协程$index")) {
                        XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} ${coroutineContext[CoroutineName]}" )
                    }
                }
                throw NullPointerException("空指针异常")
            }
            for (index in 0..10){
                launch(CoroutineName("子协程$index")) {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} ${coroutineContext[CoroutineName]}" )
                }
            }
            try {
                job.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} end")
        }
    }

    private fun testExceptionSupervisorScope (){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("exceptionHandler ${coroutineContext[CoroutineName].toString()} 处理异常 ：$throwable")
        }
        GlobalScope.launch(exceptionHandler) {
            supervisorScope {
                launch(CoroutineName("异常子协程")) {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} 我要开始抛异常了")
                    throw NullPointerException("空指针异常")
                }
                for (index in 0..10) {
                    launch(CoroutineName("子协程$index")) {
                        XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}正常执行 $index")
                        if (index %3 == 0){
                            throw NullPointerException("子协程${index}空指针异常")
                        }
                    }
                }
            }
        }
    }

    private fun testExceptionSupervisorJob(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            XLogGlobal.logger(XLogConstant.Coroutine).dThread("exceptionHandler ${coroutineContext[CoroutineName].toString()} 处理异常 ：$throwable")
        }
        val supervisorScope = CoroutineScope(SupervisorJob() + exceptionHandler)
        with(supervisorScope) {
            launch(CoroutineName("异常子协程")) {
                XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name} 我要开始抛异常了")
                throw NullPointerException("空指针异常")
            }
            for (index in 0..10) {
                launch(CoroutineName("子协程$index")) {
                    XLogGlobal.logger(XLogConstant.Coroutine).dThread("${Thread.currentThread().name}正常执行 $index")
                    if (index % 3 == 0) {
                        throw NullPointerException("子协程${index}空指针异常")
                    }
                }
            }
        }
    }

    fun doOnCreate() {
        lifecycleScope.launch {
            delay(2000)
            ToastHelper.showToast(this@CoroutineStudyActivity, "haha", Toast.LENGTH_SHORT)
        }
    }


}