package com.example.utilsuser.coroutine.juejin_sheying

class MyTT {
    private suspend fun test(){
    }
}

class Money(val value: Int) {
    operator fun plus(money: Money): Money {
        val sum = value + money.value
        return Money(sum)
    }
}

