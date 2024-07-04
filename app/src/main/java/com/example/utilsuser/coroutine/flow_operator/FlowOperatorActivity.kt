package com.example.utilsuser.coroutine.flow_operator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsuser.R
import com.example.utilsuser.coroutine.flow.FlowActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class FlowOperatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_operator)

        GuideSettings.set(
            findViewById(R.id.lv_launcher),
            arrayOf(
                GuideItemEntity("map") {
                    GlobalScope.launch {
                        val flow = flowOf(1, 2, 3, 4, 5)
                        flow.map {
                            it * it
                        }.collect {
                            LogUtil.d("$it")
                        }
                    }
                },
                GuideItemEntity("filter") {
                    GlobalScope.launch {
                        val flow = flowOf(1, 2, 3, 4, 5)
                        flow.filter {
                            it % 2 == 0
                        }.map {
                            it * it
                        }.collect {
                            LogUtil.d("$it")
                        }
                    }
                },
                GuideItemEntity("onEach") {
                    GlobalScope.launch {
                        val flow = flowOf(1, 2, 3, 4, 5)
                        flow.filter {
                            it % 2 == 0
                        }.onEach {
                            LogUtil.d("中间值: $it")
                        }.map {
                            it * it
                        }.collect {
                            LogUtil.d("$it")
                        }
                    }
                },
                GuideItemEntity("debounce") {
                    GlobalScope.launch {
                        flow {
                            emit(1)
                            emit(2)
                            delay(600)
                            emit(3)
                            delay(100)
                            emit(4)
                            delay(100)
                            emit(5)
                        }
                            .debounce(500)
                            .collect {
                                LogUtil.d("$it")
                            }
                    }
                },
                GuideItemEntity("sample") {
                    GlobalScope.launch {
                        flow<String> {
                            while (true) {
                                emit("发送一条弹幕")
                            }
                        }.sample(1000)
                            .flowOn(Dispatchers.IO)
                            .collect {
                                LogUtil.d("$it")
                            }
                    }

                },
                GuideItemEntity("reduce") {
                    GlobalScope.launch {
                        val flow = flow {
                            for (i in 1..100) {
                                emit(i)
                            }
                        }
                        val result: Int = flow.reduce { acc, value ->
                            LogUtil.d("累加值: $acc, 当前值: $value")
                            acc + value
                        }
                        LogUtil.d("$result")
                    }

                },
                GuideItemEntity("reduce") {
                    GlobalScope.launch {
                        val flow = flow {
                            for (i in 1..100) {
                                emit(i)
                            }
                        }
                        val result: Int = flow.reduce { acc, value ->
                            LogUtil.d("累加值: $acc, 当前值: $value")
                            acc + value
                        }
                        LogUtil.d("$result")
                    }

                },
                GuideItemEntity("fold") {
                    GlobalScope.launch {
                        val result = flow {
                            for (i in 'A'..'Z') {
                                emit(i.toString())
                            }
                        }.fold("Alphabet: ") { acc, value ->
                            acc + value
                        }
                        LogUtil.d("$result")
                    }

                },
                GuideItemEntity("flatMapConcat") {
                    GlobalScope.launch {
                        flowOf(1, 2, 3)
                            .flatMapConcat {
                                flowOf("a$it", "b$it")
                            }
                            .collect {
                                LogUtil.d(it)
                            }
                    }
                },
                GuideItemEntity("flatMapConcat FlowResponse") {
                    GlobalScope.launch {
                        sendGetTokenRequest()
                            .flatMapConcat { token ->
                                sendGetUserInfoRequest(token)
                            }
                            .flowOn(Dispatchers.IO)
                            .collect { userInfo ->
                                LogUtil.d(userInfo)
                            }
                    }
                },
                GuideItemEntity("flatMapConcat delay") {
                    GlobalScope.launch {
                        flowOf(300, 200, 100)
                            .flatMapConcat {
                                flow {
                                    delay(it.toLong())
                                    emit("a$it")
                                    emit("b$it")
                                }
                            }
                            .collect {
                                LogUtil.d(it)
                            }
                    }
                },
                GuideItemEntity("flatMapMerge delay") {
                    GlobalScope.launch {
                        flowOf(300, 200, 100)
                            .flatMapMerge {
                                flow {
                                    delay(it.toLong())
                                    emit("a$it")
                                    emit("b$it")
                                }
                            }
                            .collect {
                                LogUtil.d(it)
                            }
                    }
                },
                GuideItemEntity("flatMapLatest") {
                    GlobalScope.launch {
                        flow {
                            emit(1)
                            delay(150)
                            emit(2)
                            delay(50)
                            emit(3)
                        }.flatMapLatest {
                            flow {
                                delay(100)
                                emit("$it")
                            }
                        }.collect {
                            LogUtil.d(it)
                        }
                    }
                },
                GuideItemEntity("zip") {
                    GlobalScope.launch {
                        val flow1 = flowOf("a", "b", "c")
                        val flow2 = flowOf(1, 2, 3, 4, 5)
                        flow1.zip(flow2) { a, b ->
                            a + b
                        }.collect {
                            LogUtil.d(it)
                        }
                    }
                },
                GuideItemEntity("zip2") {
                    GlobalScope.launch {
                        val flow1 = flowOf("a", "b", "c", "d")
                        val flow2 = flowOf(1, 2)
                        flow1.zip(flow2) { a, b ->
                            a + b
                        }.collect {
                            LogUtil.d(it)
                        }
                    }
                },
                GuideItemEntity("zip") {
                    GlobalScope.launch {
                        val start = System.currentTimeMillis()
                        val flow1 = flow {
                            delay(3000)
                            emit("a")
                        }
                        val flow2 = flow {
                            delay(2000)
                            emit(1)
                        }
                        flow1.zip(flow2) { a, b ->
                            a + b
                        }.collect {
                            val end = System.currentTimeMillis()
                            LogUtil.d("Time cost ${end - start} ms")
                        }
                    }
                },
                GuideItemEntity("zip FlowRequest") {
                    GlobalScope.launch {
                        sendRealtimeWeatherRequest()
                            .zip(sendSevenDaysWeatherRequest()) { realtimeWeather, sevenDaysWeather ->
                                realtimeWeather + sevenDaysWeather
                            }
                            .collect { weather ->
                                LogUtil.d("天气: $weather")
                            }
                    }
                },
                GuideItemEntity("zip FlowRequest2") {
                    GlobalScope.launch {
                        val start = System.currentTimeMillis()
                        sendRealtimeWeatherRequest()
                            .zip(sendSevenDaysWeatherRequest()) { realtimeWeather, sevenDaysWeather ->
                                "$realtimeWeather | $sevenDaysWeather"
                            }
                            .zip(sendWeatherAmountOfPrecipitationRequest()) { weather, amountOfPrecipitation ->
                                "$weather | 降水量: $amountOfPrecipitation"
                            }
                            .collect {
                                LogUtil.d("天气: $it")
                                LogUtil.d("请求耗费时间: ${System.currentTimeMillis() - start}")
                            }
                    }
                },
                GuideItemEntity("未使用buffer，flow会和collect在同一协程中") {
                    GlobalScope.launch {
                        flow {
                            emit(1)
                            delay(1000)
                            emit(2)
                            delay(1000)
                            emit(3)
                        }.onEach {
                            LogUtil.d("$it is ready")
                        }.collect {
                            delay(1000)
                            LogUtil.d("$it is handled")
                        }
                    }
                },
                GuideItemEntity("使用buffer，flow会和collect在不同协程中") {
                    GlobalScope.launch {
                        flow {
                            emit(1)
                            delay(1000)
                            emit(2)
                            delay(1000)
                            emit(3)
                        }.onEach {
                            LogUtil.d("$it is ready")
                        }.buffer()
                            .collect {
                                delay(1000)
                                LogUtil.d("$it is handled")
                            }
                    }
                },
                GuideItemEntity("collectLatest 永远无法处理数据") {
                    GlobalScope.launch {
                        flow {
                            var count = 0
                            while (true) {
                                emit(count)
                                delay(1000)
                                count++
                            }
                        }.collectLatest {
                            LogUtil.d("start handle $it")
                            delay(2000)
                            LogUtil.d("finish handle $it")
                        }
                    }
                },
                GuideItemEntity("collectLatest 永远无法处理数据") {
                    GlobalScope.launch {
                        flow {
                            var count = 0
                            while (true) {
                                emit(count)
                                delay(1000)
                                count++
                            }
                        }.conflate()
                            .collect {
                                LogUtil.d("start handle $it")
                                delay(2000)
                                LogUtil.d("finish handle $it")
                            }
                    }
                },
            )
        )
    }

    fun sendGetTokenRequest(): Flow<String> = flow {
        delay(500)
        emit("temp_token_user")
    }

    fun sendGetUserInfoRequest(token: String): Flow<String> = flow {
        delay(1000)
        emit("username: hsf")
    }

    fun sendRealtimeWeatherRequest(): Flow<String> = flow {
        delay(1000)
        emit("实时天气 小雨")
    }

    fun sendSevenDaysWeatherRequest(): Flow<String> = flow {
        delay(2000)
        emit("未来七天 晴")
    }

    fun sendWeatherAmountOfPrecipitationRequest(): Flow<Float> = flow {
        delay(3000)
        emit(18.33f)
    }
}