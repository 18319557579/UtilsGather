package com.example.utilsuser.delegate

class MySet2<T>(val helperSet: HashSet<T>) : Set<T> by helperSet {
    override fun contains(element: T) = false
    fun eat() = println("I can eat.")
}