package com.example.uioperate.fragment.communication

//使用接口的方式进行通信

interface DrinkDemand {
    fun requestDrink()
}

interface EatDemand {
    fun requestEat()
}

interface MonkeyWork {
    fun startAcrobatics()
}

interface FishWork {
    fun startQquashow()
    fun playWithMe()
}

interface MonkeyCallFish {
    fun invoke()
}

