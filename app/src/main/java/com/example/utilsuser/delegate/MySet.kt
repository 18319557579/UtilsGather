package com.example.utilsuser.delegate

class MySet<T> (val helperSet: HashSet<T>) : Set<T> {
    override val size: Int
        get() = helperSet.size

    override fun isEmpty(): Boolean {
        return helperSet.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return helperSet.iterator()
    }

    override fun containsAll(elements: Collection<T>) = helperSet.containsAll(elements)
    override fun contains(element: T) = false
    fun eat() = println("I can eat.")
}