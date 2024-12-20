package com.example.utilsuser.coroutine.juejin_sheying

import com.elvishew.xlog.Logger

fun Logger.dThread(msg: String) {
    this.d(Thread.currentThread().toString() + ": " + msg)
}
fun Logger.dLine() {
    this.d("----------------------------------------------------------------------------------")
}
