package com.example.uioperate.fragment.communication_evnetbus

data class MessageEvent(var type: EventType, val sport: String) {
    enum class EventType {
        ZOO_TO_ZOO,
        ZOO_TO_MONKEY,
        ZOO_TO_FISH,



        FISH_TO_ZOO
    }
}

data class MonkeyCall(val action: String)

data class FishCall(val action: String)