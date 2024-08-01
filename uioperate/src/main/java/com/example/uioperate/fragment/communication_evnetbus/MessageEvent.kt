package com.example.uioperate.fragment.communication_evnetbus

//动物园发起的调用
data class MessageEvent(var type: EventType, val sport: String) {
    enum class EventType {
        ZOO_TO_ZOO,
        ZOO_TO_MONKEY,
        ZOO_TO_FISH,
    }
}

//猴子发起的调用
data class MonkeyCallZoo(val action: String)
data class MonkeyCallFish(val time: Long)

//鱼发起的调用
data class FishCallZoo(val action: String)